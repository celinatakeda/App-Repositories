package com.example.apprepositories.domain


import com.example.apprepositories.core.UseCase
import com.example.apprepositories.data.model.Repo
import com.example.apprepositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}