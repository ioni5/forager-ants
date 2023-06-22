package com.udacity.webcrawler.json;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {
    CrawlerConfiguration crawlerConfiguration = null;
    try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      crawlerConfiguration = ConfigurationLoader.read(reader);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return crawlerConfiguration;
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    Objects.requireNonNull(reader);
    CrawlerConfiguration crawlerConfiguration = null;
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
      crawlerConfiguration = (CrawlerConfiguration) objectMapper.readValue(reader, CrawlerConfiguration.class);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return crawlerConfiguration;
  }
}