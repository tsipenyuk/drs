// prettier-ignore
const nj = require('numjs');
const gaussian = require("./gaussian")

test("create a gaussian", () => {
  const expected = nj.array(
    [[0.001569896402509339,    0.00041568875501454025, 0.000012115412806030556],
     [0.33176940119955395,     1,                      0.33176940119955395 ],
     [0.000012115412806030556, 0.00041568875501454025, 0.001569896402509339]])
  const received = gaussian({
    nPts: [3,3],
    sigma: [0.5, 1.5],
    thetaInDegrees: 10
  })
  expect(received).toEqual(expected);
});

test("create a rotated gaussian rotated by 90 compared to the previous one", () => {
  const expected = nj.array(
    [[ 0.000012115412806030556, 0.331769401199554, 0.001569896402509339 ],
     [ 0.00041568875501454025,  1,                 0.00041568875501454025 ],
     [ 0.001569896402509339,    0.331769401199554, 0.000012115412806030556 ]])
  const received = gaussian({
    nPts: [3,3],
    sigma: [0.5, 1.5],
    thetaInDegrees: 100
  })
  expect(received).toEqual(expected);
});
