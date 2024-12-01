package api.goraebab.domain.blueprint.service;

import api.goraebab.domain.blueprint.dockerObject.ProcessedData;

import java.util.List;
import java.util.Map;

/**
 * Service interface for synchronizing Docker containers with blueprint data.
 * Provides methods to manage Docker container operations based on the given processed data.
 *
 * @author whitem4rk
 * @version 1.0
 */
public interface DockerSyncService {

    List<Map<String, Object>> syncDockerWithBlueprintData(ProcessedData processedData);

}