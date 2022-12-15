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

  // rotation matrix
  const thetaPi = thetaInDegrees * Math.PI / 180
  const ct = Math.cos(thetaPi)
  const st = Math.sin(thetaPi)
  const Omx = nj.array([[ct, -st], [st, ct]])
  const tOmx = nj.array([[ct, st], [-st, ct]])

  // ensure correct variance
  const sigma2 = nj.multiply(sigma, sigma)
  const sigma2h = nj.divide(sigma2, 2)
  const Dmx = nj.diag(nj.divide(nj.ones(2), sigma2h))

  // combine rotation and variance
  const DOmx = nj.dot(Dmx, Omx)
  const Amx = nj.dot(tOmx, DOmx)

  const xGrid = stepRange(xMin, xMax, nPts[0])
  const yGrid = stepRange(yMin, yMax, nPts[1])
  let gaussian = nj.zeros(nPts)

  for (const ix of Array(nPts[0]).keys()) {
    for (const iy of Array(nPts[0]).keys()) {
      const x = xGrid.get(ix)
      const y = yGrid.get(iy)
      const v = nj.subtract([x,y], mean)
      const mv = nj.subtract(mean, [x,y])
      const arg = nj.dot(mv, nj.dot(Amx, v)).tolist()[0]
      const g = amplitude * Math.exp(arg)
      gaussian.set(ix,iy,g)
    }
  }
  return gaussian
}

module.exports = gaussian;
