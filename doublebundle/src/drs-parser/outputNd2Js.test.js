const nj = require('numjs');
const outputNd2Js = require("./outputNd2Js")

test("In output, replace ndarrays with regular js arrays", () => {
  const plainInput = [[1,2], [3,4]]

  const input = {
    plaintext: [{name: "four", value: 4}],
    heatmaps: [{name: "gaussian", value: nj.array(plainInput)}]
  }

  const expected = {
    plaintext: [{name: "four", value: 4}],
    heatmaps: [{name: "gaussian", value: plainInput}]
  }

  const received = outputNd2Js(input)
  expect(received).toEqual(expected);
});
