const nj = require("numjs");

/**
 * Like nj.arange, but requires num of steps & includes stop
 */
function stepRange(start, stop, numSteps) {
  const delta = (stop - start) / (numSteps - 1)
  const overstepToIncludeStop = stop + 0.5 * delta
  return nj.arange(start, overstepToIncludeStop, delta)
}

module.exports = stepRange
