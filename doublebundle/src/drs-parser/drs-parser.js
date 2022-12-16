const gaussian = require("./gaussian")
const outputNd2Js = require("./outputNd2Js")

function parse(userInput) {
  // inject library functions
  const parsedScript = new Function(
    "gaussian",
    userInput
  )
  const result = parsedScript(gaussian)

  console.debug(`User input: ${userInput}`)
  console.debug(`Parsed script: ${parsedScript}`)
  console.debug(`Result:`, result)


  console.warn(outputNd2Js(result))
  return outputNd2Js(result)
}

export { parse }
