package com.example.smartmenu.similarity

class SmartTextMatcher(
    private val threshold: Double = 0.45,
    private val topK: Int = 3
) {

    // =========================
    // PUBLIC API
    // =========================
    fun match(
        userInputs: List<String>,
        menuItems: List<String>
    ): Map<String, List<MatchResult>> {

        val normalizedMenu = menuItems.map {
            it to normalize(it)
        }

        return userInputs.associateWith { userInput ->
            val normalizedUser = normalize(userInput)

            normalizedMenu.map { (originalMenu, normalizedMenuText) ->
                val score = similarityScore(normalizedUser, normalizedMenuText)
                MatchResult(originalMenu, score)
            }
                .filter { it.score >= threshold }
                .sortedByDescending { it.score }
                .take(topK)
        }
    }

    // =========================
    // 1️⃣ Normalize
    // =========================
    private fun normalize(text: String): String {
        return text
            .lowercase()
            .replace(Regex("[ًٌٍَُِّْ]"), "") // tashkeel
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
            .replace("ة", "ه")
            .replace("ى", "ي")
            .replace(Regex("[^a-z0-9ا-ي\\s]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    // =========================
    // 3️⃣ Similarity Scoring
    // =========================
    private fun similarityScore(a: String, b: String): Double {
        if (a.isEmpty() || b.isEmpty()) return 0.0

        val levenshtein = levenshteinSimilarity(a, b)
        val token = tokenOverlapScore(a, b)
        val partial = if (b.contains(a) || a.contains(b)) 1.0 else 0.0

        return (0.4 * levenshtein) +
                (0.4 * token) +
                (0.2 * partial)
    }

    // -------------------------
    // Levenshtein Similarity
    // -------------------------
    private fun levenshteinSimilarity(a: String, b: String): Double {
        val distance = levenshteinDistance(a, b)
        val maxLen = maxOf(a.length, b.length)
        return if (maxLen == 0) 1.0 else 1.0 - distance.toDouble() / maxLen
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        val dp = Array(a.length + 1) { IntArray(b.length + 1) }

        for (i in 0..a.length) dp[i][0] = i
        for (j in 0..b.length) dp[0][j] = j

        for (i in 1..a.length) {
            for (j in 1..b.length) {
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + if (a[i - 1] == b[j - 1]) 0 else 1
                )
            }
        }
        return dp[a.length][b.length]
    }

    // -------------------------
    // Token Overlap
    // -------------------------
    private fun tokenOverlapScore(a: String, b: String): Double {
        val tokensA = a.split(" ").toSet()
        val tokensB = b.split(" ").toSet()

        if (tokensA.isEmpty() || tokensB.isEmpty()) return 0.0

        val intersection = tokensA.intersect(tokensB).size
        return intersection.toDouble() / tokensA.size
    }
}
