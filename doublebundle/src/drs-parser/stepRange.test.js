const nj = require('numjs');
const stepRange = require("./stepRange")

test("simple stepRange", () => {
  const expected = nj.array([-1,0,1])
  const received = stepRange(-1,1,3)
  expect(received).toEqual(expected);
});

test("more complex stepRange", () => {
  const expected = nj.array([0, 0.5, 1, 1.5, 2])
  const received = stepRange(0,2,5)
  expect(received).toEqual(expected);
});
