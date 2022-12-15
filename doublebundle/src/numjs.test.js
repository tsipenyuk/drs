const nj = require('numjs');
const r2c = require("./drs-parser/r2c")

test('create an array', () => {
  const expected = nj.array([[0,0,0],[0,0,0]])
  const received = nj.zeros([2,3])
  expect(received).toEqual(expected);
});


// proof-of-concept: fft is not too slow on js
test("time of Fourier transform", () => {
  const x = r2c(nj.ones([127,127]))
  const start = console.time("20 (i)ffts of a 127x127 array took")

  for (const ix of Array(10).keys()) {
    const xHat = nj.fft(x)
    const xCheck = nj.ifft(xHat)
  }

  // 185 ms in jest on my machine - Arseniy Tsipenyuk
  console.timeEnd("20 (i)ffts of a 127x127 array took")
  expect(true).toEqual(true);
});
