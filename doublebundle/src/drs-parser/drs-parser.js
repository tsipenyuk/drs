function parse(userInput) {
  const parsedScript = new Function(userInput)
  const result = parsedScript()

  console.debug(`User input: ${userInput}`)
  console.debug(`Parsed script: ${parsedScript}`)
  console.debug(`Result: ${result}`)

  return result
}

export { parse }
