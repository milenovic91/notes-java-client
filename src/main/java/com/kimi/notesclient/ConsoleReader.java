package com.kimi.notesclient;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleReader {
  public static <T> T read(Class<T> c) throws InstantiationException, IllegalAccessException {
    T result = c.newInstance();
    String[] parts = c.getName().split("\\.");
    String className = parts[parts.length - 1];
    for (Field f : c.getDeclaredFields()) {
      Object val = System.console().readLine("%s[%s]: ", className, f.getName());
      if (f.getType() == int.class) {
        val = Integer.parseInt((String)val);
      }
      f.set(result, val);
    }
    return result;
  }

  public static int select(List<String> options) {
    try {
      if (options != null && !options.isEmpty()) {
        String output = IntStream.range(0, options.size())
          .mapToObj(i -> Integer.toString(i) + ". " + options.get(i))
          .collect(Collectors.joining("\n"));
        String input = System.console().readLine("%s\n", output);
        int result = Integer.parseInt(input);
        if (result < 0 || result > options.size()) {
          return -1;
        }
        return result;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
    return -1;
  }
}
