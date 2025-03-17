package com.example.cursofirebaselite.domain

import com.example.cursofirebaselite.data.Repository

class CanAccessToApp {

    val repository = Repository()

    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion()
        val minAllowedVersion = repository.getMinAllowedVersion()

        for ((currentePart, minVersionPart) in currentVersion.zip(minAllowedVersion)) {
            if (currentePart != minVersionPart) {
                return currentePart > minVersionPart
            }
        }
        return true
    }
}