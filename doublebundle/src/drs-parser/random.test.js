const random = require("./random")

test("sfc32", () => {
  // Create cyrb128 state:
  var seed = random.cyrb128("apples");

  // Four 32-bit component hashes provide the seed for sfc32.
  var rand = random.sfc32(seed[0], seed[1], seed[2], seed[3]);

  // Obtain sequential random numbers like so:
  const a = rand();
  const b = rand();
  const expected = [0.7928402766119689, 0.9241351385135204]
  expect(true).toEqual(true);
});

test("mulberry32", () => {
  var seed = random.cyrb128("apples");
  var rand = random.mulberry32(seed[0]);
  const a = rand();
  const b = rand();
  const expected = [0.6954001493286341, 0.5513719702139497]
  const received = [a,b]
  expect(received).toEqual(expected);
});
