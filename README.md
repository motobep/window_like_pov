# window-like-pov

## Setup

The setup consists of two parts:
1) This mod, that spins up a server to get data about the face position.
2) [window_like_screen repo](https://github.com/motobep/window_like_screen) that gets data of the face position and sends it to the mod server.

**Installation:**
- Install fabric.
- Get (build or download from workflow artifacts) the mod and put it in minecraft's mod folder.
- Run minecraft, press 'k' to activate mod.
- Run [window_like_screen repo](https://github.com/motobep/window_like_screen), open it in your browser and click 'Toggle send' button to send camera position to the mod.

## Dependencies

- Fabric
- Gradle (to build)
- Java
- Node + npm
- Web browser
- Your Camera

## Build

```bash
./gradlew build
```


### Setup Fabric

For setup instructions, please see the [Fabric Documentation page](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up) related to the IDE that you are using.

### License

This template is available under the CC0 license. Feel free to learn from it and incorporate it in your own projects.

