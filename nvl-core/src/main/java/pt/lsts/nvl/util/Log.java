package pt.lsts.nvl.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public final class Log {
  /**
   * Id.
   */
  private final String logId;

  /**
   * Output streams.
   */
  private final List<PrintStream> streams = new LinkedList<>();


  /**
   * Constructor.
   * 
   * @param id
   *          Log id.
   * @param file
   *          Output file.
   */
  public Log(String id) {
    logId = id;
  }

  public void writeTo(File file) {
    try {
      writeTo(new PrintStream(new BufferedOutputStream(new FileOutputStream(file))));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void writeTo(PrintStream out) {
    streams.add(out);
  }

  public void message(String fmt, Object... args) {
    message("", fmt, args);
  }
  
  public void message(String type, String fmt, Object... args) {
    double t = Clock.wallTime();
    synchronized (this) {
      for (PrintStream out : streams) {
        out.printf("%s|%.3f|%s|", logId, t, type);
        out.printf(fmt, args);
        out.println();
        out.flush();
      }
    }
  }

}