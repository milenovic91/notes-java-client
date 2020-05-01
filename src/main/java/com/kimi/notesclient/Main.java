package com.kimi.notesclient;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.kimi.notesclient.model.User;
import com.kimi.notesclient.model.input.NotesInput;
import com.kimi.notesclient.model.input.PagingParams;
import com.kimi.notesclient.model.input.UserInput;

public class Main {
  static enum Choice {
    LIST, GET, END
  }

  public static Choice getChoice() {
    int c = ConsoleReader.select(Arrays.asList(Choice.values())
    .stream()
    .map(i -> i.name())
    .collect(Collectors.toList()));
    return c != -1 ? Choice.values()[c] : Choice.END;
  }
  public static void main(String args[]) throws IOException, InterruptedException, ExecutionException,
      InstantiationException, IllegalAccessException {
    UserInput input = ConsoleReader.read(UserInput.class);
    User user = NotesService.login(input.username, input.password).get();
    System.out.println("You have successfully logged in.");
    Choice choice = getChoice();
    while (choice != Choice.END) {
      try {
        switch (choice) {
          case LIST:
            PagingParams params = ConsoleReader.read(PagingParams.class);
            Object o = NotesService.getNotes(user.getToken(), params.page, params.pageSize);
            System.out.println(o.toString());
            break;
          case GET:
            NotesInput notesInput = ConsoleReader.read(NotesInput.class);
            // TODO - finish it
          default:
            break;
        }
        choice = getChoice();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
