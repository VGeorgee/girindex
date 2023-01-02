const fs = require('fs');
const allData = JSON.parse(fs.readFileSync('data.json'));

// Sort the data by timestamp in reverse order
reverseAllData = [...allData].sort((a, b) => new Date(b.date) - new Date(a.date));

function getTimeSeries() {
  let timeSeries = [];

  for (let current of allData) {
    let otherEntries = findAllBeforeTimestamp(current.date, current.place);
    let totalPrice = current.price;
    for (let other of otherEntries) {
      totalPrice += other.price;
    }
    let averagePrice = totalPrice / (otherEntries.length + 1);
    timeSeries.push({
      place: current.place,
      date: current.date,
      price: averagePrice
    });
  }
  return timeSeries;
}


function findAllBeforeTimestamp(timestamp, place) {

  reverseAllData.sort((a, b) => new Date(b.date) - new Date(a.date));
  // Create a set of distinct places from the data
  let places = new Set(allData.map(data => data.place));
  // Initialize the result list
  let result = [];

  // Iterate over the set of places
  for (let currentPlace of places) {
    // Skip the current place if it's the same as the parameter
    if (currentPlace === place) {
      continue;
    }

    // Find the first entry in the sorted list with the same place as the current place
    // and with a date less than the parameter
    let entry = reverseAllData.find(data => data.place === currentPlace && new Date(data.date) < new Date(timestamp));
    console.log({entry})
    // If an entry was found, add it to the result list
    if (entry) {
      result.push(entry);
    }
  }

  return result;
}

let timeSeries = getTimeSeries();
fs.writeFileSync('avg.json', JSON.stringify(timeSeries, null, 2));