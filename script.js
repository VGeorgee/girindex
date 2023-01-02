const fs = require('fs');

function getTimeSeries() {
  let timeSeries = [];
  let allData = JSON.parse(fs.readFileSync('data.json'));
  for (let current of allData) {
    let otherEntries = this.findAllBeforeTimestamp(current.timestamp, current.place);
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