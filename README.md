# Standard

Features:
- Zero-indirection atomic instructions using weaker memory order modes over on or off-heap memory
- Collection/array to each other transformation, unmodifiable array views, array builder
- Lazy initialization, start many threads together
- Global `Thread.UncaughtExceptionHandler` framework
- `InputStream` to `String`, read/write `.lst` file, common `DirectoryStream.Filter`s
- Rethrow checked exceptions, terminate thread printing only a message
- Alternative to `MethodHandle` using bytecode generation
- Meaningful condition checking
- Class copying to inline polymorphic calls
- Nullable and immutable annotation
- Use `Method` and `Constructor` interchangeably
- Write to `PrintStream` without generating garbage
- Find next power of two of number, mix two `int`s into a `long`
- Native library loading
- Global application name, collection methods for arrays/`Path`s, single-threaded/multi-threaded counter and variable toggle, read/write `.properties` file, common `Pattern`s, replace in `Path`
- Data types: file extension, percent
- Swing: disable all focus in `JFrame`, shorter alternative to `SwingUtilities.invokeLater()`, single-threaded `ListModel`, `JTextField` with placeholder, common `FileFilter`s, listen to `JList` element clicks
- Listen to GCs, log them, global `Cleaner` framework, execute different code based on platform, use all `Unsafe` methods
- Get platform information, including number of cores and number of hardware threads per core
- Bind threads to CPUs, set their priority to realtime
- Get/set system properties, get common ones and common system paths
- Sleep 0.5ms on Windows, use `nanosleep()` on Linux
- Format time durations

Includes:
- Benchmarking framework
- Parallel unit testing framework (requires IntelliJ IDEA)
- Zero-configuration C++/JAR build tool (requires IntelliJ IDEA)
