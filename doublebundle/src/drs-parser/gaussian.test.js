const nj = require('numjs');
const gaussian = require("./gaussian")

test("create a gaussian", () => {
  const expected = nj.array([[-2,-1,0],[-1,0,1],[0,1,2]])
  const received = gaussian({nPts: [3,3]})
  expect(received).toEqual(expected);
});
