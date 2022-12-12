const nj = require('numjs');

/**
 * Convert a real-valued nj array to a complex one
 */
function r2c(realArray) {
  const shape = realArray.shape
  const withComplexDimension = [...shape, 1]
  return nj.concatenate(
    nj.reshape(realArray, withComplexDimension),
    nj.zeros(withComplexDimension)
  )
}

module.exports = r2c
