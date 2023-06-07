package com.project.passwordmanager.model

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * This interface serves as the Data Access Object (DAO) for the Credential entity.
 * It provides methods to interact with the credentials_table in the database.
 */
@Dao
interface CredentialDao {

    /**
     * Inserts a new credential into the credentials_table.
     *
     * @param credential The Credential object to be inserted.
     */
    @Insert
    suspend fun insert(credential: Credential)

    /**
     * Updates an existing credential in the credentials_table.
     *
     * @param credential The updated Credential object.
     */
    @Update
    suspend fun update(credential: Credential)

    /**
     * Deletes a credential from the credentials_table.
     *
     * @param credential The Credential object to be deleted.
     */
    @Delete
    suspend fun delete(credential: Credential)

    /**
     * Retrieves all the credentials from the credentials_table in ascending order by ID.
     * The result is wrapped in a LiveData object, allowing for automatic updates in the UI.
     *
     * @return A LiveData object containing a list of all credentials.
     */
    @Query("SELECT * FROM credentials_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Credential>>

    /**
     * Retrieves all the credentials from the credentials_table in ascending order by date.
     * The result is wrapped in a LiveData object, allowing for automatic updates in the UI.
     *
     * @return A LiveData object containing a list of all credentials sorted by date.
     */
    @Query("SELECT * FROM credentials_table ORDER BY date")
    fun getSortedByDate(): LiveData<List<Credential>>

    /**
     * Retrieves a credential from the credentials_table based on its ID.
     *
     * @param credentialId The ID of the credential to retrieve.
     * @return The retrieved Credential object, or null if not found.
     */
    @Query("SELECT * FROM credentials_table WHERE id = :credentialId")
    fun getCredentialById(credentialId: Int): Credential?

    /**
     * Retrieves all the credentials from the credentials_table in ascending order by username.
     * The result is wrapped in a LiveData object, allowing for automatic updates in the UI.
     *
     * @return A LiveData object containing a list of all credentials sorted by username.
     */
    @Query("SELECT * FROM credentials_table ORDER BY username")
    fun getSortedByUsername(): LiveData<List<Credential>>

    /**
     * Retrieves all the credentials from the credentials_table in ascending order by service.
     * The result is wrapped in a LiveData object, allowing for automatic updates in the UI.
     *
     * @return A LiveData object containing a list of all credentials sorted by service.
     */
    @Query("SELECT * FROM credentials_table ORDER BY service")
    fun getSortedByService(): LiveData<List<Credential>>
}
