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
package com.evgtrush.foodbag.presentation.recipes.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.evgtrush.foodbag.R
import com.evgtrush.foodbag.databinding.FragmentRecipeDetailsBinding
import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.presentation.utils.hideBottomNav

class RecipeDetailsFragment: Fragment() {

    private val args: RecipeDetailsFragmentArgs by navArgs()

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNav()

        val recipe = args.recipe
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = recipe?.name
            }
            btnShare.setOnClickListener { shareRecipe(recipe) }
            Glide
                .with(root.context)
                .load(recipe?.imageUrl)
                .placeholder(R.drawable.ic_outline_restaurant_24)
                .into(image)
            description.text = recipe?.description
        }
    }

    private fun shareRecipe(recipe: Recipe?) {
        recipe?.let {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, contactToString(it))
            }

            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }
    }

    private fun contactToString(recipe: Recipe?):String =
        """
                Recipe: ${recipe?.name},
                Description: ${recipe?.description} 
        """.trimIndent()
}