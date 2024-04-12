package com.snjx.kelimaca.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageModel {
   String event;
   String data;
   String messageId;
}
