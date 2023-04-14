package dev.vivekraman.fix.subscription.dates.controller;

import dev.vivekraman.fix.subscription.dates.constants.ApiPath;
import dev.vivekraman.fix.subscription.dates.model.response.UpdateQueries;
import dev.vivekraman.fix.subscription.dates.service.api.CorrectionService;
import dev.vivekraman.fix.subscription.dates.service.api.IngestService;
import dev.vivekraman.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiPath.BASE_URL)
@RequiredArgsConstructor
public class FixSubscriptionDatesController implements ApiPath {
  private final IngestService ingestService;
  private final CorrectionService correctionService;

  @PostMapping(path = PROCESS, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Response<UpdateQueries> process(
      @RequestPart("subsInfoFile") MultipartFile subsInfoFile,
    @RequestPart("scheduleInfoFile") MultipartFile scheduleInfoFile) throws Exception {
    this.ingestService.readSubscriptionInfoFile(subsInfoFile);
    this.ingestService.readScheduleInfoFile(scheduleInfoFile);
    UpdateQueries queries = this.correctionService.prepareQueries();
    return Response.of(queries);
  }
}
