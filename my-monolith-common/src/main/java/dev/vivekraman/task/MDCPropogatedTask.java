package dev.vivekraman.task;

import org.slf4j.MDC;

import java.util.Map;

public abstract class MDCPropogatedTask implements Runnable {
  private final Map<String, String> mdcContextMap;

  protected MDCPropogatedTask() {
    this.mdcContextMap = MDC.getCopyOfContextMap();
  }

  @Override
  public void run() {
    MDC.setContextMap(mdcContextMap);
    this.execute();
    MDC.clear();
  }

  public abstract void execute();
}
