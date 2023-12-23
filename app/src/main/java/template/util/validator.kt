package template.util

class Validator {

    companion object {
        fun isDateCorrect(date: String): Boolean {
            // this works but i dont get why 3 in the end, chatgpt regex is buggy
            val regex = Regex(
                "^(0[1-9]|[1-2][0-9]|3[0-1])" +
                    "/(0[1-9]|1[0-2])" +
                    "/(19|20)\\d{2}",
            )
            return regex.matches(date)
        }
    }
}
