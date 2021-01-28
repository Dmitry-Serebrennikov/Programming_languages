import java.lang.Exception

class SuchRuleExists: Exception()
class NoRulesException: Exception()

class Validator {

    val rules = mutableListOf<Rule>()

    var errors = mutableListOf<String>()

    fun validate(password: String): Boolean {
        if (rules.isEmpty()) {
            throw NoRulesException()
        }

        errors = mutableListOf<String>()
        for (rule in rules) {
            if (!rule.check(password)) {
                errors.add(rule.description)
            }
        }
        return errors.isEmpty()
    }

    fun addRule(newRule: Rule) {
        for (rule in rules) {
            if (rule::class == newRule::class) {
                throw SuchRuleExists()
            }
        }
        rules.add(newRule)
    }
}