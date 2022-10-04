package com.example.braingame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.braingame.R
import com.example.braingame.databinding.FragmentGameFinishedBinding
import com.example.braingame.domain.entity.GameResult
import com.example.braingame.domain.entity.Level

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private val gameResult by lazy {
        args.gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
        isWinner()
        fillStrings()
    }

    private fun isWinner(){
        if (gameResult.winner){
            binding.emojiResult.setImageDrawable(ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_smile
            ))
        } else {
            binding.emojiResult.setImageDrawable(ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_sad
            ))
        }
    }

    private fun fillStrings(){
        binding.tvRequiredAnswers.text = String.format(
            requireContext().resources.getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers
        )
        binding.tvScoreAnswers.text = String.format(
            requireContext().resources.getString(R.string.score_answers),
            gameResult.countOfRightAnswers
        )
        binding.tvRequiredPercentage.text = String.format(
            requireContext().resources.getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers
        )
        binding.tvScorePercentage.text = String.format(
            requireContext().resources.getString(R.string.score_percentage),
            (gameResult.countOfRightAnswers/gameResult.countOfQuestions.toDouble() * 100).toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}