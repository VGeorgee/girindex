const fs = require('fs');

function getTimeSeries() {
  let timeSeries = [];
  let allData = JSON.parse(fs.readFileSync('data.json'));
  for (let current of allData) {
    let otherEntries = findAllBeforeTimestamp(current.timestamp, current.place);
    let totalPrice = current.price;
    for (let other of otherEntries) {
      totalPrice += other.price;
    }
    let averagePrice = totalPrice / (otherEntries.length + 1);
    timeSeries.push({
      place: current.place,
      date: current.timestamp,
      price: averagePrice
    });
  }
  return timeSeries;
}


function findAllBeforeTimestamp(timestamp, place) {
  // Read all data from the database
  let allData = this.getAll();

  // Sort the data by timestamp in reverse order
  allData.sort((a, b) => -a.timestamp.compareTo(b.timestamp));

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
    // and with a timestamp less than the parameter
    let entry = allData.find(data => data.place === currentPlace && data.timestamp.isBefore(timestamp));

    // If an entry was found, add it to the result list
    if (entry) {
      result.push(entry);
    }
  }

  return result;
}