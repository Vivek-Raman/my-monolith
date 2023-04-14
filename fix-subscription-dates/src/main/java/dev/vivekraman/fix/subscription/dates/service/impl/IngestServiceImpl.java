package dev.vivekraman.fix.subscription.dates.service.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import dev.vivekraman.fix.subscription.dates.entity.ScheduleInfo;
import dev.vivekraman.fix.subscription.dates.entity.SubscriptionInfo;
import dev.vivekraman.fix.subscription.dates.repository.ScheduleInfoRepository;
import dev.vivekraman.fix.subscription.dates.repository.SubscriptionInfoRepository;
import dev.vivekraman.fix.subscription.dates.service.api.IngestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngestServiceImpl implements IngestService {
  private final SubscriptionInfoRepository subscriptionInfoRepository;
  private final ScheduleInfoRepository scheduleInfoRepository;

  @Override
  public void readSubscriptionInfoFile(MultipartFile file) throws IOException {
    InputStream inputStream = file.getInputStream();
    String fileName = file.getOriginalFilename();

    CsvSchema schema = CsvSchema.builder()
        .addColumn("subscriptionId")
        .addColumn("bundleId")
        .addColumn("correctEarliestOrderProcessingDate")
        .addColumn("subscriptionFrequency")
        .setUseHeader(false).build();
    CsvMapper mapper = new CsvMapper();
    MappingIterator<SubscriptionInfo> iterator =
        mapper.readerFor(SubscriptionInfo.class)
            .with(schema).readValues(inputStream);
    AtomicInteger processedRowCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);

    while (iterator.hasNext()) {
      SubscriptionInfo row = null;
      try {
        row = iterator.next();
        if (processedRowCount.getAndIncrement() % 1 == 0) {
          log.debug("Processed {} row(s) of file \"{}\".", processedRowCount.get(), fileName);
        }
      } catch (Exception e) {
        if (processedRowCount.get() == 0) continue;
        log.error("Failed to read row {} of file \"{}\", error ", processedRowCount.get(),
            fileName, e);
        failureCount.incrementAndGet();
        if (failureCount.get() > 3) {
          log.error("Too many ({}) failed row reads. Please check the file \"{}\".",
              failureCount.get(), fileName);
          break;
        }
      }
      if (Objects.nonNull(row)) {
        this.subscriptionInfoRepository.save(row);
      }
    }

    log.info("Processed file \"{}\" {} rows with {} errors.", fileName, processedRowCount.get(),
        failureCount.get());
  }

  @Override
  public void readScheduleInfoFile(MultipartFile scheduleInfoFile) throws IOException {
    InputStream inputStream = scheduleInfoFile.getInputStream();
    String fileName = scheduleInfoFile.getOriginalFilename();

    CsvSchema schema = CsvSchema.builder()
        .addColumn("scheduleId")
        .addColumn("orderProcessingDate")
        .addColumn("subscriptionId")
        .setUseHeader(false).build();
    CsvMapper mapper = new CsvMapper();
    MappingIterator<ScheduleInfo> iterator =
        mapper.readerFor(ScheduleInfo.class)
            .with(schema).readValues(inputStream);
    AtomicInteger processedRowCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);

    while (iterator.hasNext()) {
      ScheduleInfo row = null;
      try {
        row = iterator.next();
        if (processedRowCount.getAndIncrement() % 1 == 0) {
          log.debug("Processed {} row(s) of file \"{}\".", processedRowCount.get(), fileName);
        }
      } catch (Exception e) {
        if (processedRowCount.get() == 0) continue;
        log.error("Failed to read row {} of file \"{}\", error ", processedRowCount.get(),
            fileName, e);
        failureCount.incrementAndGet();
        if (failureCount.get() > 3) {
          log.error("Too many ({}) failed row reads. Please check the file \"{}\".",
              failureCount.get(), fileName);
          break;
        }
      }
      if (Objects.nonNull(row)) {
        this.scheduleInfoRepository.save(row);
      }
    }

    log.info("Processed file \"{}\" {} rows with {} errors.", fileName, processedRowCount.get(),
        failureCount.get());
  }
}
