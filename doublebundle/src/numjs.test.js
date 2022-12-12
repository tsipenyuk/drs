const nj = require('numjs');

test('create an array', () => {
  const expected = nj.array([[0,0,0],[0,0,0]])
  const received = nj.zeros([2,3])
  expect(received).toEqual(expected);
});
