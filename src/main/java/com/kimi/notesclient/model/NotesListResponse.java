package com.kimi.notesclient.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotesListResponse {
  public List<Note> entries;

  public String toString() {
    if (entries != null && entries.size() > 0) {
      return entries.stream().map(Note::toString).collect(Collectors.joining("\n"));
    }
    return null;
  }
}