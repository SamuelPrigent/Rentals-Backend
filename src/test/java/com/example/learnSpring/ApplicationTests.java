// package com.example.back;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import java.io.ByteArrayOutputStream;
// import java.io.PrintStream;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.springframework.beans.factory.annotation.Autowired;
// import com.example.back.service.Service;
// import com.example.back.HelloWorld.HelloWorld;

// @SpringBootTest
// class ApplicationTests {

// // Variables
// @Autowired
// private Service hs;

// private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
// private final PrintStream originalOut = System.out;

// // // Before
// // @BeforeEach
// // void setUpStreams() {
// // System.setOut(new PrintStream(outContent));
// // }

// // // After
// // @AfterEach
// // void restoreStreams() {
// // System.setOut(originalOut);
// // }

// // ========== TESTS ==========
// // @Test
// // void contextLoads() {
// // }

// // @Test
// // void testSayHello() {
// // Application.sayHello();
// // String output = outContent.toString();
// // assertTrue(output.contains("Hello Springboot"));
// // }

// // @Test
// // void testGetHelloWorld() {
// // HelloWorld hw = hs.getHelloWorld();
// // assertEquals("Hello World !", hw.toString());
// // }
// }
