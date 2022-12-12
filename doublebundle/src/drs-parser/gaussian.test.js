const nj = require('numjs');
const gaussian = require("./gaussian")

test("create a gaussian", () => {
  const expected = nj.array([[1,1,1],[1,1,1]])
  const received = gaussian({nPts: [2,3]})
  expect(received).toEqual(expected);
});
