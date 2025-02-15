package io.edurt.datacap.fs.minio

import io.edurt.datacap.fs.Fs
import io.edurt.datacap.fs.FsRequest
import io.edurt.datacap.fs.FsResponse
import io.edurt.datacap.fs.minio.MinIOUtils.Companion.copy
import org.slf4j.LoggerFactory.getLogger
import java.io.File
import java.lang.String.join

class MinIOFs : Fs
{
    private val log = getLogger(this::class.java)

    override fun writer(request: FsRequest?): FsResponse
    {
        requireNotNull(request) { "request must not be null" }

        log.info("{} writer origin path [ {} ]", this.name(), request.fileName)
        val targetPath = join(File.separator, request.endpoint, request.bucket, request.fileName)
        val response = FsResponse.builder()
            .origin(request.fileName)
            .remote(targetPath)
            .successful(true)
            .build()
        log.info("{} writer target path [ {} ]", this.name(), request.fileName)
        try
        {
            val key = copy(request, request.stream, request.fileName)
            response.remote = key
            log.info("{} writer [ {} ] successfully", this.name(), key)
        }
        catch (e: Exception)
        {
            log.error("{} writer error", this.name(), e)
            response.isSuccessful = false
            response.message = e.message
        }
        return response
    }

    override fun reader(request: FsRequest?): FsResponse
    {
        requireNotNull(request) { "request must not be null" }

        log.info("{} reader origin path [ {} ]", this.name(), request.fileName)
        val response = FsResponse.builder()
            .remote(request.fileName)
            .successful(true)
            .build()
        try
        {
            response.context = MinIOUtils.reader(request)
            log.info("{} reader [ {} ] successfully", this.name(), request.fileName)
        }
        catch (e: java.lang.Exception)
        {
            log.error("{} reader error", this.name(), e)
            response.isSuccessful = false
            response.message = e.message
        }
        return response
    }

    override fun delete(request: FsRequest?): FsResponse
    {
        requireNotNull(request) { "request must not be null" }

        try
        {
            val status = MinIOUtils.delete(request)
            log.info("{} delete [ {} ] successfully", this.name(), request.fileName)
            return FsResponse.builder()
                .successful(status)
                .build()
        }
        catch (e: java.lang.Exception)
        {
            log.error("{} delete error", this.name(), e)
            return FsResponse.builder()
                .successful(false)
                .message(e.message)
                .build()
        }
    }
}
