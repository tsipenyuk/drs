/**
 * Convert nj.ndarrays in user's script output to js arrays
 */
function ndToJsArray(ndArray) {
  return ndArray.tolist()
}

function value2JsArray({name, value}) {
  return {name, value: ndToJsArray(value)}
}

function allValues2JsArrays(arr) {
  return arr.map(value2JsArray)
}

function outputNd2Js(output) {
  return {
    ...output,
    heatmaps: allValues2JsArrays(output.heatmaps)
  }
}

module.exports = outputNd2Js
