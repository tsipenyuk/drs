const nj = require('numjs');
const r2c = require("./r2c")

test("convert a real array to a complex one", () => {
  const input = nj.ones([4,4])
  const expected = nj.concatenate(nj.ones([4,4,1]), nj.zeros([4,4,1]))
  const received = r2c(input)
  expect(received).toEqual(expected);
});
