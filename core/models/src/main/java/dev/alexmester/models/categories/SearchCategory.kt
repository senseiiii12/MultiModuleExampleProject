package dev.alexmester.models.categories

enum class SearchCategory(
    val code: String,
    val displayName: String,
    val emoji: String
) {
    POLITICS("politics", "Politics", "🏛️"),
    SPORTS("sports", "Sports", "⚽"),
    BUSINESS("business", "Business", "💼"),
    TECHNOLOGY("technology", "Technology", "💻"),
    ENTERTAINMENT("entertainment", "Entertainment", "🎬"),
    HEALTH("health", "Health", "🏥"),
    SCIENCE("science", "Science", "🔬"),
    LIFESTYLE("lifestyle", "Lifestyle", "🌿"),
    TRAVEL("travel", "Travel", "✈️"),
    CULTURE("culture", "Culture", "🎨"),
    EDUCATION("education", "Education", "📚"),
    ENVIRONMENT("environment", "Environment", "🌍");
}