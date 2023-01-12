package com.evgtrush.foodbag.domain.repositories

import com.evgtrush.foodbag.domain.models.Recipe

interface RecipeRepository {

    suspend fun getRecipes(): List<Recipe>
}