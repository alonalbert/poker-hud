package com.karamba.android.pokerhud;

import android.util.Log;

public class LogUtils {

  /**
   * Default TAG
   */
  public static final String TAG = "PokerHUD";

  /**
   * Priority constant for the println method; use LogUtils.v.
   */
  public static final int VERBOSE = Log.VERBOSE;

  /**
   * Priority constant for the println method; use LogUtils.d.
   */
  public static final int DEBUG = Log.DEBUG;

  /**
   * Priority constant for the println method; use LogUtils.i.
   */
  public static final int INFO = Log.INFO;

  /**
   * Priority constant for the println method; use LogUtils.w.
   */
  public static final int WARN = Log.WARN;

  /**
   * Priority constant for the println method; use LogUtils.e.
   */
  public static final int ERROR = Log.ERROR;

  /**
   * Checks to see whether or not a log for the default tag at the specified level.
   */
  @SuppressWarnings("unused")
  public static boolean isLoggable(int level) {
    return Log.isLoggable(TAG, level);
  }


  private static final char[] HEX_ALPHABET = "0123456789ABCDEF".toCharArray();


  /**
   * Send a {@link #VERBOSE} log message.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int v(String format, Object... args) {
    if (isLoggable(TAG, VERBOSE)) {
      if (args.length != 0) {
        return Log.v(TAG, String.format(format, args));
      } else {
        return Log.v(TAG, format);
      }
    }
    return 0;
  }


  /**
   * Send a {@link #VERBOSE} log message.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int v(Throwable tr, String format, Object... args) {
    if (isLoggable(TAG, VERBOSE)) {
      if (args.length != 0) {
        return Log.v(TAG, String.format(format, args), tr);
      } else {
        return Log.v(TAG, format, tr);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #DEBUG} log message.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int d(String format, Object... args) {
    if (isLoggable(TAG, DEBUG)) {
      if (args.length != 0) {
        return Log.d(TAG, String.format(format, args));
      } else {
        return Log.d(TAG, format);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #DEBUG} log message.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int d(Throwable tr, String format, Object... args) {
    if (isLoggable(TAG, DEBUG)) {
      if (args.length != 0) {
        return Log.d(TAG, String.format(format, args), tr);
      } else {
        return Log.d(TAG, format, tr);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #INFO} log message.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  public static int i(String format, Object... args) {
    if (isLoggable(TAG, INFO)) {
      if (args.length != 0) {
        return Log.i(TAG, String.format(format, args));
      } else {
        return Log.i(TAG, format);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #INFO} log message.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int i(Throwable tr, String format, Object... args) {
    if (isLoggable(TAG, INFO)) {
      if (args.length != 0) {
        return Log.i(TAG, String.format(format, args), tr);
      } else {
        return Log.i(TAG, format, tr);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #WARN} log message.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int w(String format, Object... args) {
    if (isLoggable(TAG, WARN)) {
      if (args.length != 0) {
        return Log.w(TAG, String.format(format, args));
      } else {
        return Log.w(TAG, format);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #WARN} log message.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int w(Throwable tr, String format, Object... args) {
    if (isLoggable(TAG, WARN)) {
      if (args.length != 0) {
        return Log.w(TAG, String.format(format, args), tr);
      } else {
        return Log.w(TAG, format, tr);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #ERROR} log message.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int e(String format, Object... args) {
    if (isLoggable(TAG, ERROR)) {
      if (args.length != 0) {
        return Log.e(TAG, String.format(format, args));
      } else {
        return Log.e(TAG, format);
      }
    }
    return 0;
  }

  /**
   * Send a {@link #ERROR} log message.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int e(Throwable tr, String format, Object... args) {
    if (isLoggable(TAG, ERROR)) {
      if (args.length != 0) {
        return Log.e(TAG, String.format(format, args), tr);
      } else {
        return Log.e(TAG, format, tr);
      }
    }
    return 0;
  }

  /**
   * What a Terrible Failure: Report a condition that should never happen. The error will always
   * be logged at level ASSERT with the call stack. Depending on system configuration, a report
   * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
   * immediately with an error dialog.
   *
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int wtf(String format, Object... args) {
    if (args.length != 0) {
      return Log.wtf(TAG, String.format(format, args), new Error());
    } else {
      return Log.wtf(TAG, format, new Error());
    }
  }

  /**
   * What a Terrible Failure: Report a condition that should never happen. The error will always
   * be logged at level ASSERT with the call stack. Depending on system configuration, a report
   * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
   * immediately with an error dialog.
   *
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("UnusedDeclaration")
  public static int wtf(Throwable tr, String format, Object... args) {
    if (args.length != 0) {
      return Log.wtf(TAG, String.format(format, args), tr);
    } else {
      return Log.wtf(TAG, format, tr);
    }
  }

  /**
   * Checks to see whether or not a log for the specified tag is loggable at the specified level.
   */
  public static boolean isLoggable(String tag, int level) {
    return Log.isLoggable(tag, level);
  }

  /**
   * Send a {@link #VERBOSE} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int verbose(String tag, String format, Object... args) {
    if (isLoggable(tag, VERBOSE)) {
      return Log.v(tag, String.format(format, args));
    }
    return 0;
  }


  /**
   * Send a {@link #VERBOSE} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int verbose(String tag, Throwable tr, String format, Object... args) {
    if (isLoggable(tag, VERBOSE)) {
      return Log.v(tag, String.format(format, args), tr);
    }
    return 0;
  }

  /**
   * Send a {@link #DEBUG} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int debug(String tag, String format, Object... args) {
    if (isLoggable(tag, DEBUG)) {
      return Log.d(tag, String.format(format, args));
    }
    return 0;
  }

  /**
   * Send a {@link #DEBUG} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int debug(String tag, Throwable tr, String format, Object... args) {
    if (isLoggable(tag, DEBUG)) {
      return Log.d(tag, String.format(format, args), tr);
    }
    return 0;
  }

  /**
   * Send a {@link #INFO} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int info(String tag, String format, Object... args) {
    if (isLoggable(tag, INFO)) {
      return Log.i(tag, String.format(format, args));
    }
    return 0;
  }

  /**
   * Send a {@link #INFO} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int info(String tag, Throwable tr, String format, Object... args) {
    if (isLoggable(tag, INFO)) {
      return Log.i(tag, String.format(format, args), tr);
    }
    return 0;
  }

  /**
   * Send a {@link #WARN} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int warning(String tag, String format, Object... args) {
    if (isLoggable(tag, WARN)) {
      return Log.w(tag, String.format(format, args));
    }
    return 0;
  }

  /**
   * Send a {@link #WARN} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int warning(String tag, Throwable tr, String format, Object... args) {
    if (isLoggable(tag, WARN)) {
      return Log.w(tag, String.format(format, args), tr);
    }
    return 0;
  }

  /**
   * Send a {@link #ERROR} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int error(String tag, String format, Object... args) {
    if (isLoggable(tag, ERROR)) {
      return Log.e(tag, String.format(format, args));
    }
    return 0;
  }

  /**
   * Send a {@link #ERROR} log message.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("unused")
  public static int error(String tag, Throwable tr, String format, Object... args) {
    if (isLoggable(tag, ERROR)) {
      return Log.e(tag, String.format(format, args), tr);
    }
    return 0;
  }

  /**
   * What a Terrible Failure: Report a condition that should never happen. The error will always
   * be logged at level ASSERT with the call stack. Depending on system configuration, a report
   * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
   * immediately with an error dialog.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("UnusedDeclaration")
  public static int wtf(String tag, String format, Object... args) {
    return Log.wtf(tag, String.format(format, args), new Error());
  }

  /**
   * What a Terrible Failure: Report a condition that should never happen. The error will always
   * be logged at level ASSERT with the call stack. Depending on system configuration, a report
   * may be added to the {@link android.os.DropBoxManager} and/or the process may be terminated
   * immediately with an error dialog.
   *
   * @param tag Used to identify the source of a log message.  It usually identifies the class or
   * activity where the log call occurs.
   * @param tr An exception to log
   * @param format the format string (see {@link java.util.Formatter#format})
   * @param args the list of arguments passed to the formatter. If there are more arguments than
   * required by {@code format}, additional arguments are ignored.
   */
  @SuppressWarnings("UnusedDeclaration")
  public static int wtf(String tag, Throwable tr, String format, Object... args) {
    return Log.wtf(tag, String.format(format, args), tr);
  }

  /**
   * Convert a byte array to a loggable hex string
   */
  @SuppressWarnings("unused")
  public static String bytesToHex(byte[] bytes) {
    char[] hex = new char[bytes.length * 2];
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hex[j * 2] = HEX_ALPHABET[v >>> 4];
      hex[j * 2 + 1] = HEX_ALPHABET[v & 0x0F];
    }
    return new String(hex);
  }
}