package dev.vivekraman.inventory.history.analysis.repository;

import dev.vivekraman.inventory.history.analysis.entity.AnalysisJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisJobRepository extends CrudRepository<AnalysisJob, String> {}
