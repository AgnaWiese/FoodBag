/*
 * Copyright (C) 2023. Evgenia Trushkina
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evgtrush.foodbag.data.repositories

import com.evgtrush.foodbag.data.datasources.network.NetworkRecipeDataSource
import com.evgtrush.foodbag.data.mappers.RecipeMapper
import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.domain.repositories.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val dataSource: NetworkRecipeDataSource,
    private val mapper: RecipeMapper,
    private val dispatcher: CoroutineDispatcher
): RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> = withContext(dispatcher) {
        dataSource.getRecipes().map {
            mapper.convert(it)
        }
    }
}