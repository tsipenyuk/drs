const nj = require("numjs");
const stepRange = require("./stepRange")

const defaultParams = {
  nPts: [101, 101],
  xMin: -1,
  xMax: 1,
  yMin: -1,
  yMax: 1,
  amplitude: 1,
  mean: [0, 0],
  sigma: [0.05, 0.05],
  thetaInDegrees: 0
}

function gaussian(
  userParams = {}
) {
  const {
    nPts,
    xMin,
    xMax,
    yMin,
    yMax,
    amplitude,
    mean,
    sigma,
    thetaInDegrees
  } = {...defaultParams, ...userParams}

  const thetaPi = thetaInDegrees * Math.PI / 180
  const xGrid = stepRange(xMin, xMax, nPts[0])
  const yGrid = stepRange(yMin, yMax, nPts[1])
  let gaussian = nj.zeros(nPts)

  for (const ix of Array(nPts[0]).keys()) {
    for (const iy of Array(nPts[0]).keys()) {
      const x = xGrid.get(ix)
      const y = yGrid.get(iy)
      gaussian.set(ix,iy, x+y)
    }
  }
  console.log(xGrid)
  console.log(yGrid)
  console.log(gaussian)
  // nj.arange()
  // const res = nj.concatenate(nj.ones([4,4,1]), nj.zeros([4,4,1]))
  // const res = nj.arange(24).reshape(3,4,2)
  // const fres = nj.fft(res)
  return gaussian
}

module.exports = gaussian;
