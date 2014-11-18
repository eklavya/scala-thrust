//stdIn.write_line("{\"_id\":1,\"_action\":\"create\",\"_type\":\"window\",\"_method\":\"\",\"_args\":{\"root_url\":\"http://breach.cc/\",\"title\":\"Go Thrust\",\"size\":{\"width\":1024,\"height\":768},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}");
//stdIn.write_line("--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--");
//stdIn.write_line("{\"_id\":2,\"_action\":\"call\",\"_method\":\"show\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}");
//stdIn.write_line("--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--");
//stdIn.write_line("{\"_id\":3,\"_action\":\"call\",\"_method\":\"maximize\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}");
//stdIn.write_line("--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--");
//stdIn.write_line("{\"_id\":4,\"_action\":\"call\",\"_method\":\"focus\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":true}}");
//stdIn.write_line("--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--");

package com.eklavya.thrust

object Main extends App {
  val pb = new ProcessBuilder("/Users/eklavya/Documents/thrust/out/Debug/ThrustShell.app/Contents/MacOS/ThrustShell")
  pb.redirectErrorStream(true)
  val p = pb.start
  p.getOutputStream.write((new String("{\"_id\":1,\"_action\":\"create\",\"_type\":\"window\",\"_method\":\"\",\"_args\":{\"root_url\":\"http://breach.cc/\",\"title\":\"Go Thrust\",\"size\":{\"width\":1024,\"height\":768},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--")).getBytes)
  p.getOutputStream.write((new String("{\"_id\":2,\"_action\":\"call\",\"_method\":\"show\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--")).getBytes)
  p.getOutputStream.write((new String("{\"_id\":3,\"_action\":\"call\",\"_method\":\"maximize\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":false}}\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--")).getBytes)
  p.getOutputStream.write((new String("{\"_id\":4,\"_action\":\"call\",\"_method\":\"focus\",\"_target\":1,\"_args\":{\"size\":{},\"value\":false,\"cookie_store\":false,\"off_the_record\":false,\"focus\":true}}\n--(Foo)++__THRUST_SHELL_BOUNDARY__++(Bar)--")).getBytes)
  p.getOutputStream.flush()
}