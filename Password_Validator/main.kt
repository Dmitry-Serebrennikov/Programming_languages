fun main() {

    println("Please enter your password... \nPassword must contain 8-40 characters, including numbers, lowercase and uppercase Latin letters and special characters:\n>>")
    val password = readLine().toString()
    val validator = Validator()
    validator.addRule(lengthValidation())
    validator.addRule(upperLowerCase())
    validator.addRule(specSigns())
    validator.addRule(noDictinaryWords())
    validator.addRule(entropyLevel())
    
    if (validator.validate(password)) {
        println("The password was successfully accepted. All conditions are met!")
    } else {
        println(validator.errors)
    }
}