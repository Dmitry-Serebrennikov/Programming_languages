import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Test {
    @Test
    fun checkPassword() {
        val password : String = "qwerty"
        val validator = Validator()
        validator.addRule(lengthValidation())
        validator.addRule(upperLowerCase())
        validator.addRule(specSigns())
        validator.addRule(noDictinaryWords())
        validator.addRule(entropyLevel())

        assertEquals(true, validator.validate(password))
    }

    @Test
    fun checkPassword2() {

        val password : String = "1123581321"
        val validator = Validator()
        validator.addRule(lengthValidation())
        validator.addRule(upperLowerCase())
        validator.addRule(specSigns())
        validator.addRule(noDictinaryWords())
        validator.addRule(entropyLevel())

        assertEquals(true, validator.validate(password))
    }

    @Test
    fun checkPassword3() {

        val password : String = "@vAlidator14321"
        val validator = Validator()
        validator.addRule(lengthValidation())
        validator.addRule(upperLowerCase())
        validator.addRule(specSigns())
        validator.addRule(noDictinaryWords())
        validator.addRule(entropyLevel())

        assertEquals(true, validator.validate(password))
    }

    @Test
    fun checkPassword4() {

        val password : String = "Eeeeeeeeeeeeeeeeee1#"
        val validator = Validator()
        validator.addRule(lengthValidation())
        validator.addRule(upperLowerCase())
        validator.addRule(specSigns())
        validator.addRule(noDictinaryWords())
        validator.addRule(entropyLevel())

        assertEquals(true, validator.validate(password))
    }

    @Test
    fun checkPassword5() {

        val password : String = "kakVzlomat'4Labs"
        val validator = Validator()
        validator.addRule(lengthValidation())
        validator.addRule(upperLowerCase())
        validator.addRule(specSigns())
        validator.addRule(noDictinaryWords())
        validator.addRule(entropyLevel())

        assertEquals(true, validator.validate(password))
    }
}