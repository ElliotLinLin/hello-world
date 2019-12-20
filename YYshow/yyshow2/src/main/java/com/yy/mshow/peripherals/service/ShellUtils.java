package com.yy.mshow.peripherals.service;

import java.util.List;

public class ShellUtils {
  public static final String COMMAND_EXIT = "exit\n";
  
  public static final String COMMAND_LINE_END = "\n";
  
  public static final String COMMAND_SH = "sh";
  
  public static final String COMMAND_SU = "su";
  
  private ShellUtils() { throw new AssertionError(); }
  
  public static boolean checkRootPermission() { return ((execCommand("echo root", true, false)).result == 0); }
  
  public static CommandResult execCommand(String paramString, boolean paramBoolean) { return execCommand(new String[] { paramString }, paramBoolean, true); }
  
  public static CommandResult execCommand(String paramString, boolean paramBoolean1, boolean paramBoolean2) { return execCommand(new String[] { paramString }, paramBoolean1, paramBoolean2); }
  
  public static CommandResult execCommand(List<String> paramList, boolean paramBoolean) {
    if (paramList == null) {
      paramList = null;
      return execCommand(paramList, paramBoolean, true);
    } 
    String[] arrayOfString = (String[])paramList.toArray(new String[0]);
    return execCommand(arrayOfString, paramBoolean, true);
  }
  
  public static CommandResult execCommand(List<String> paramList, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramList == null) {
      paramList = null;
      return execCommand(paramList, paramBoolean1, paramBoolean2);
    } 
    String[] arrayOfString = (String[])paramList.toArray(new String[0]);
    return execCommand(arrayOfString, paramBoolean1, paramBoolean2);
  }
  
  public static CommandResult execCommand(String[] paramArrayOfString, boolean paramBoolean) { return execCommand(paramArrayOfString, paramBoolean, true); }
  
  public static CommandResult execCommand(String[] paramArrayOfString, boolean paramBoolean1, boolean paramBoolean2) { // Byte code:
    //   0: iconst_m1
    //   1: istore_3
    //   2: aload_0
    //   3: ifnull -> 11
    //   6: aload_0
    //   7: arraylength
    //   8: ifne -> 22
    //   11: new com/yy/mshow/peripherals/service/ShellUtils$CommandResult
    //   14: dup
    //   15: iconst_m1
    //   16: aconst_null
    //   17: aconst_null
    //   18: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   21: areturn
    //   22: aconst_null
    //   23: astore #35
    //   25: aconst_null
    //   26: astore #36
    //   28: aconst_null
    //   29: astore #11
    //   31: aconst_null
    //   32: astore #20
    //   34: aconst_null
    //   35: astore #12
    //   37: aconst_null
    //   38: astore #32
    //   40: aconst_null
    //   41: astore #23
    //   43: aconst_null
    //   44: astore #19
    //   46: aconst_null
    //   47: astore #18
    //   49: aconst_null
    //   50: astore #30
    //   52: aconst_null
    //   53: astore #33
    //   55: aconst_null
    //   56: astore #22
    //   58: aconst_null
    //   59: astore #17
    //   61: aconst_null
    //   62: astore #25
    //   64: aconst_null
    //   65: astore #29
    //   67: aconst_null
    //   68: astore #24
    //   70: aconst_null
    //   71: astore #26
    //   73: aconst_null
    //   74: astore #31
    //   76: aconst_null
    //   77: astore #21
    //   79: aconst_null
    //   80: astore #28
    //   82: aconst_null
    //   83: astore #34
    //   85: aconst_null
    //   86: astore #27
    //   88: aload #33
    //   90: astore #13
    //   92: aload #34
    //   94: astore #14
    //   96: aload #11
    //   98: astore #10
    //   100: aload #32
    //   102: astore #15
    //   104: aload #35
    //   106: astore #9
    //   108: aload #36
    //   110: astore #16
    //   112: invokestatic getRuntime : ()Ljava/lang/Runtime;
    //   115: astore #37
    //   117: iload_1
    //   118: ifeq -> 236
    //   121: ldc 'su'
    //   123: astore #8
    //   125: aload #33
    //   127: astore #13
    //   129: aload #34
    //   131: astore #14
    //   133: aload #11
    //   135: astore #10
    //   137: aload #32
    //   139: astore #15
    //   141: aload #35
    //   143: astore #9
    //   145: aload #36
    //   147: astore #16
    //   149: aload #37
    //   151: aload #8
    //   153: invokevirtual exec : (Ljava/lang/String;)Ljava/lang/Process;
    //   156: astore #11
    //   158: aload #33
    //   160: astore #13
    //   162: aload #34
    //   164: astore #14
    //   166: aload #11
    //   168: astore #10
    //   170: aload #32
    //   172: astore #15
    //   174: aload #11
    //   176: astore #9
    //   178: aload #11
    //   180: astore #16
    //   182: new java/io/DataOutputStream
    //   185: dup
    //   186: aload #11
    //   188: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   191: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   194: astore #8
    //   196: iload_3
    //   197: istore #4
    //   199: iload_3
    //   200: istore #5
    //   202: aload_0
    //   203: arraylength
    //   204: istore #7
    //   206: iconst_0
    //   207: istore #6
    //   209: iload #6
    //   211: iload #7
    //   213: if_icmpge -> 415
    //   216: aload_0
    //   217: iload #6
    //   219: aaload
    //   220: astore #9
    //   222: aload #9
    //   224: ifnonnull -> 243
    //   227: iload #6
    //   229: iconst_1
    //   230: iadd
    //   231: istore #6
    //   233: goto -> 209
    //   236: ldc 'sh'
    //   238: astore #8
    //   240: goto -> 125
    //   243: iload_3
    //   244: istore #4
    //   246: iload_3
    //   247: istore #5
    //   249: aload #8
    //   251: aload #9
    //   253: invokevirtual getBytes : ()[B
    //   256: invokevirtual write : ([B)V
    //   259: iload_3
    //   260: istore #4
    //   262: iload_3
    //   263: istore #5
    //   265: aload #8
    //   267: ldc '\\n'
    //   269: invokevirtual writeBytes : (Ljava/lang/String;)V
    //   272: iload_3
    //   273: istore #4
    //   275: iload_3
    //   276: istore #5
    //   278: aload #8
    //   280: invokevirtual flush : ()V
    //   283: goto -> 227
    //   286: astore #10
    //   288: aload #8
    //   290: astore_0
    //   291: aload #19
    //   293: astore #16
    //   295: aload #24
    //   297: astore #9
    //   299: iload #4
    //   301: istore_3
    //   302: aload #21
    //   304: astore #12
    //   306: aload #10
    //   308: astore #8
    //   310: aload #17
    //   312: astore #13
    //   314: aload_0
    //   315: astore #14
    //   317: aload #11
    //   319: astore #10
    //   321: aload #16
    //   323: astore #15
    //   325: aload #8
    //   327: invokevirtual printStackTrace : ()V
    //   330: aload_0
    //   331: ifnull -> 338
    //   334: aload_0
    //   335: invokevirtual close : ()V
    //   338: aload #16
    //   340: ifnull -> 348
    //   343: aload #16
    //   345: invokevirtual close : ()V
    //   348: aload #17
    //   350: ifnull -> 358
    //   353: aload #17
    //   355: invokevirtual close : ()V
    //   358: aload #12
    //   360: astore #8
    //   362: iload_3
    //   363: istore #4
    //   365: aload #9
    //   367: astore_0
    //   368: aload #11
    //   370: ifnull -> 388
    //   373: aload #11
    //   375: invokevirtual destroy : ()V
    //   378: aload #9
    //   380: astore_0
    //   381: iload_3
    //   382: istore #4
    //   384: aload #12
    //   386: astore #8
    //   388: aload_0
    //   389: ifnonnull -> 827
    //   392: aconst_null
    //   393: astore_0
    //   394: aload #8
    //   396: ifnonnull -> 835
    //   399: aconst_null
    //   400: astore #8
    //   402: new com/yy/mshow/peripherals/service/ShellUtils$CommandResult
    //   405: dup
    //   406: iload #4
    //   408: aload_0
    //   409: aload #8
    //   411: invokespecial <init> : (ILjava/lang/String;Ljava/lang/String;)V
    //   414: areturn
    //   415: iload_3
    //   416: istore #4
    //   418: iload_3
    //   419: istore #5
    //   421: aload #8
    //   423: ldc 'exit\\n'
    //   425: invokevirtual writeBytes : (Ljava/lang/String;)V
    //   428: iload_3
    //   429: istore #4
    //   431: iload_3
    //   432: istore #5
    //   434: aload #8
    //   436: invokevirtual flush : ()V
    //   439: iload_3
    //   440: istore #4
    //   442: iload_3
    //   443: istore #5
    //   445: aload #11
    //   447: invokevirtual waitFor : ()I
    //   450: istore_3
    //   451: aload #31
    //   453: astore #9
    //   455: aload #30
    //   457: astore #10
    //   459: aload #29
    //   461: astore_0
    //   462: iload_2
    //   463: ifeq -> 697
    //   466: iload_3
    //   467: istore #4
    //   469: iload_3
    //   470: istore #5
    //   472: new java/lang/StringBuilder
    //   475: dup
    //   476: invokespecial <init> : ()V
    //   479: astore_0
    //   480: new java/lang/StringBuilder
    //   483: dup
    //   484: invokespecial <init> : ()V
    //   487: astore #10
    //   489: new java/io/BufferedReader
    //   492: dup
    //   493: new java/io/InputStreamReader
    //   496: dup
    //   497: aload #11
    //   499: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   502: invokespecial <init> : (Ljava/io/InputStream;)V
    //   505: invokespecial <init> : (Ljava/io/Reader;)V
    //   508: astore #9
    //   510: new java/io/BufferedReader
    //   513: dup
    //   514: new java/io/InputStreamReader
    //   517: dup
    //   518: aload #11
    //   520: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   523: invokespecial <init> : (Ljava/io/InputStream;)V
    //   526: invokespecial <init> : (Ljava/io/Reader;)V
    //   529: astore #12
    //   531: aload #9
    //   533: invokevirtual readLine : ()Ljava/lang/String;
    //   536: astore #13
    //   538: aload #13
    //   540: ifnull -> 553
    //   543: aload_0
    //   544: aload #13
    //   546: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: pop
    //   550: goto -> 531
    //   553: aload #12
    //   555: invokevirtual readLine : ()Ljava/lang/String;
    //   558: astore #13
    //   560: aload #13
    //   562: ifnull -> 681
    //   565: aload #10
    //   567: aload #13
    //   569: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: goto -> 553
    //   576: astore #13
    //   578: aload #9
    //   580: astore #16
    //   582: aload_0
    //   583: astore #9
    //   585: aload #8
    //   587: astore_0
    //   588: aload #12
    //   590: astore #17
    //   592: aload #10
    //   594: astore #12
    //   596: aload #13
    //   598: astore #8
    //   600: aload #17
    //   602: astore #13
    //   604: aload_0
    //   605: astore #14
    //   607: aload #11
    //   609: astore #10
    //   611: aload #16
    //   613: astore #15
    //   615: aload #8
    //   617: invokevirtual printStackTrace : ()V
    //   620: aload_0
    //   621: ifnull -> 628
    //   624: aload_0
    //   625: invokevirtual close : ()V
    //   628: aload #16
    //   630: ifnull -> 638
    //   633: aload #16
    //   635: invokevirtual close : ()V
    //   638: aload #17
    //   640: ifnull -> 648
    //   643: aload #17
    //   645: invokevirtual close : ()V
    //   648: aload #12
    //   650: astore #8
    //   652: iload_3
    //   653: istore #4
    //   655: aload #9
    //   657: astore_0
    //   658: aload #11
    //   660: ifnull -> 388
    //   663: aload #11
    //   665: invokevirtual destroy : ()V
    //   668: aload #12
    //   670: astore #8
    //   672: iload_3
    //   673: istore #4
    //   675: aload #9
    //   677: astore_0
    //   678: goto -> 388
    //   681: aload #10
    //   683: astore #13
    //   685: aload #12
    //   687: astore #10
    //   689: aload #9
    //   691: astore #12
    //   693: aload #13
    //   695: astore #9
    //   697: aload #8
    //   699: ifnull -> 707
    //   702: aload #8
    //   704: invokevirtual close : ()V
    //   707: aload #12
    //   709: ifnull -> 717
    //   712: aload #12
    //   714: invokevirtual close : ()V
    //   717: aload #10
    //   719: ifnull -> 727
    //   722: aload #10
    //   724: invokevirtual close : ()V
    //   727: aload #11
    //   729: ifnull -> 1238
    //   732: aload #11
    //   734: invokevirtual destroy : ()V
    //   737: aload #9
    //   739: astore #8
    //   741: iload_3
    //   742: istore #4
    //   744: goto -> 388
    //   747: astore #8
    //   749: aload #8
    //   751: invokevirtual printStackTrace : ()V
    //   754: goto -> 727
    //   757: astore_0
    //   758: aload_0
    //   759: invokevirtual printStackTrace : ()V
    //   762: goto -> 358
    //   765: astore_0
    //   766: aload_0
    //   767: invokevirtual printStackTrace : ()V
    //   770: goto -> 648
    //   773: astore #8
    //   775: aload #14
    //   777: astore_0
    //   778: aload_0
    //   779: ifnull -> 786
    //   782: aload_0
    //   783: invokevirtual close : ()V
    //   786: aload #15
    //   788: ifnull -> 796
    //   791: aload #15
    //   793: invokevirtual close : ()V
    //   796: aload #13
    //   798: ifnull -> 806
    //   801: aload #13
    //   803: invokevirtual close : ()V
    //   806: aload #10
    //   808: ifnull -> 816
    //   811: aload #10
    //   813: invokevirtual destroy : ()V
    //   816: aload #8
    //   818: athrow
    //   819: astore_0
    //   820: aload_0
    //   821: invokevirtual printStackTrace : ()V
    //   824: goto -> 806
    //   827: aload_0
    //   828: invokevirtual toString : ()Ljava/lang/String;
    //   831: astore_0
    //   832: goto -> 394
    //   835: aload #8
    //   837: invokevirtual toString : ()Ljava/lang/String;
    //   840: astore #8
    //   842: goto -> 402
    //   845: astore #9
    //   847: aload #8
    //   849: astore_0
    //   850: aload #22
    //   852: astore #13
    //   854: aload #11
    //   856: astore #10
    //   858: aload #23
    //   860: astore #15
    //   862: aload #9
    //   864: astore #8
    //   866: goto -> 778
    //   869: astore #9
    //   871: aload #8
    //   873: astore_0
    //   874: aload #22
    //   876: astore #13
    //   878: aload #11
    //   880: astore #10
    //   882: aload #23
    //   884: astore #15
    //   886: aload #9
    //   888: astore #8
    //   890: goto -> 778
    //   893: astore #9
    //   895: aload #8
    //   897: astore_0
    //   898: aload #22
    //   900: astore #13
    //   902: aload #11
    //   904: astore #10
    //   906: aload #23
    //   908: astore #15
    //   910: aload #9
    //   912: astore #8
    //   914: goto -> 778
    //   917: astore #12
    //   919: aload #8
    //   921: astore_0
    //   922: aload #22
    //   924: astore #13
    //   926: aload #11
    //   928: astore #10
    //   930: aload #9
    //   932: astore #15
    //   934: aload #12
    //   936: astore #8
    //   938: goto -> 778
    //   941: astore #14
    //   943: aload #8
    //   945: astore_0
    //   946: aload #12
    //   948: astore #13
    //   950: aload #11
    //   952: astore #10
    //   954: aload #9
    //   956: astore #15
    //   958: aload #14
    //   960: astore #8
    //   962: goto -> 778
    //   965: astore #8
    //   967: aload #26
    //   969: astore #12
    //   971: aload #18
    //   973: astore #17
    //   975: aload #28
    //   977: astore_0
    //   978: aload #9
    //   980: astore #11
    //   982: aload #25
    //   984: astore #9
    //   986: aload #20
    //   988: astore #16
    //   990: goto -> 600
    //   993: astore #9
    //   995: aload #8
    //   997: astore_0
    //   998: aload #9
    //   1000: astore #8
    //   1002: aload #26
    //   1004: astore #12
    //   1006: aload #18
    //   1008: astore #17
    //   1010: iload #5
    //   1012: istore_3
    //   1013: aload #25
    //   1015: astore #9
    //   1017: aload #20
    //   1019: astore #16
    //   1021: goto -> 600
    //   1024: astore #12
    //   1026: aload #8
    //   1028: astore #10
    //   1030: aload_0
    //   1031: astore #9
    //   1033: aload #12
    //   1035: astore #8
    //   1037: aload #26
    //   1039: astore #12
    //   1041: aload #18
    //   1043: astore #17
    //   1045: aload #10
    //   1047: astore_0
    //   1048: aload #20
    //   1050: astore #16
    //   1052: goto -> 600
    //   1055: astore #13
    //   1057: aload #8
    //   1059: astore #9
    //   1061: aload #10
    //   1063: astore #12
    //   1065: aload_0
    //   1066: astore #10
    //   1068: aload #13
    //   1070: astore #8
    //   1072: aload #18
    //   1074: astore #17
    //   1076: aload #9
    //   1078: astore_0
    //   1079: aload #10
    //   1081: astore #9
    //   1083: aload #20
    //   1085: astore #16
    //   1087: goto -> 600
    //   1090: astore #14
    //   1092: aload #8
    //   1094: astore #13
    //   1096: aload #10
    //   1098: astore #12
    //   1100: aload_0
    //   1101: astore #10
    //   1103: aload #9
    //   1105: astore #16
    //   1107: aload #14
    //   1109: astore #8
    //   1111: aload #18
    //   1113: astore #17
    //   1115: aload #13
    //   1117: astore_0
    //   1118: aload #10
    //   1120: astore #9
    //   1122: goto -> 600
    //   1125: astore #8
    //   1127: aload #21
    //   1129: astore #12
    //   1131: aload #27
    //   1133: astore_0
    //   1134: aload #16
    //   1136: astore #11
    //   1138: aload #24
    //   1140: astore #9
    //   1142: aload #19
    //   1144: astore #16
    //   1146: goto -> 310
    //   1149: astore #12
    //   1151: aload #8
    //   1153: astore #10
    //   1155: aload_0
    //   1156: astore #9
    //   1158: aload #12
    //   1160: astore #8
    //   1162: aload #21
    //   1164: astore #12
    //   1166: aload #10
    //   1168: astore_0
    //   1169: aload #19
    //   1171: astore #16
    //   1173: goto -> 310
    //   1176: astore #13
    //   1178: aload #8
    //   1180: astore #9
    //   1182: aload #10
    //   1184: astore #12
    //   1186: aload_0
    //   1187: astore #10
    //   1189: aload #13
    //   1191: astore #8
    //   1193: aload #9
    //   1195: astore_0
    //   1196: aload #10
    //   1198: astore #9
    //   1200: aload #19
    //   1202: astore #16
    //   1204: goto -> 310
    //   1207: astore #14
    //   1209: aload #8
    //   1211: astore #13
    //   1213: aload #10
    //   1215: astore #12
    //   1217: aload_0
    //   1218: astore #10
    //   1220: aload #9
    //   1222: astore #16
    //   1224: aload #14
    //   1226: astore #8
    //   1228: aload #13
    //   1230: astore_0
    //   1231: aload #10
    //   1233: astore #9
    //   1235: goto -> 310
    //   1238: aload #9
    //   1240: astore #8
    //   1242: iload_3
    //   1243: istore #4
    //   1245: goto -> 388
    //   1248: astore #15
    //   1250: aload #8
    //   1252: astore #13
    //   1254: aload #10
    //   1256: astore #14
    //   1258: aload_0
    //   1259: astore #10
    //   1261: aload #12
    //   1263: astore #17
    //   1265: aload #9
    //   1267: astore #16
    //   1269: aload #15
    //   1271: astore #8
    //   1273: aload #14
    //   1275: astore #12
    //   1277: aload #13
    //   1279: astore_0
    //   1280: aload #10
    //   1282: astore #9
    //   1284: goto -> 310
    // Exception table:
    //   from	to	target	type
    //   112	117	1125	java/io/IOException
    //   112	117	965	java/lang/Exception
    //   112	117	773	finally
    //   149	158	1125	java/io/IOException
    //   149	158	965	java/lang/Exception
    //   149	158	773	finally
    //   182	196	1125	java/io/IOException
    //   182	196	965	java/lang/Exception
    //   182	196	773	finally
    //   202	206	286	java/io/IOException
    //   202	206	993	java/lang/Exception
    //   202	206	845	finally
    //   249	259	286	java/io/IOException
    //   249	259	993	java/lang/Exception
    //   249	259	845	finally
    //   265	272	286	java/io/IOException
    //   265	272	993	java/lang/Exception
    //   265	272	845	finally
    //   278	283	286	java/io/IOException
    //   278	283	993	java/lang/Exception
    //   278	283	845	finally
    //   325	330	773	finally
    //   334	338	757	java/io/IOException
    //   343	348	757	java/io/IOException
    //   353	358	757	java/io/IOException
    //   421	428	286	java/io/IOException
    //   421	428	993	java/lang/Exception
    //   421	428	845	finally
    //   434	439	286	java/io/IOException
    //   434	439	993	java/lang/Exception
    //   434	439	845	finally
    //   445	451	286	java/io/IOException
    //   445	451	993	java/lang/Exception
    //   445	451	845	finally
    //   472	480	286	java/io/IOException
    //   472	480	993	java/lang/Exception
    //   472	480	845	finally
    //   480	489	1149	java/io/IOException
    //   480	489	1024	java/lang/Exception
    //   480	489	869	finally
    //   489	510	1176	java/io/IOException
    //   489	510	1055	java/lang/Exception
    //   489	510	893	finally
    //   510	531	1207	java/io/IOException
    //   510	531	1090	java/lang/Exception
    //   510	531	917	finally
    //   531	538	1248	java/io/IOException
    //   531	538	576	java/lang/Exception
    //   531	538	941	finally
    //   543	550	1248	java/io/IOException
    //   543	550	576	java/lang/Exception
    //   543	550	941	finally
    //   553	560	1248	java/io/IOException
    //   553	560	576	java/lang/Exception
    //   553	560	941	finally
    //   565	573	1248	java/io/IOException
    //   565	573	576	java/lang/Exception
    //   565	573	941	finally
    //   615	620	773	finally
    //   624	628	765	java/io/IOException
    //   633	638	765	java/io/IOException
    //   643	648	765	java/io/IOException
    //   702	707	747	java/io/IOException
    //   712	717	747	java/io/IOException
    //   722	727	747	java/io/IOException
    //   782	786	819	java/io/IOException
    //   791	796	819	java/io/IOException
    //   801	806	819	java/io/IOException }
  
  public static class CommandResult {
    public String errorMsg;
    
    public int result;
    
    public String successMsg;
    
    public CommandResult(int param1Int) { this.result = param1Int; }
    
    public CommandResult(int param1Int, String param1String1, String param1String2) {
      this.result = param1Int;
      this.successMsg = param1String1;
      this.errorMsg = param1String2;
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\service\ShellUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */