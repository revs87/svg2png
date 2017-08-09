# Free SVG to PNG converter 

## Goal
Manage your Icons in SVG and generate the needed PNG into your projects as needed. No "Web Service" needed, just an executable JAR file.

## Download & Requirements

* [Download latest release version of svg2png](https://github.com/puel/svg2png/releases)
* You have to have Java 8 installed on your PC
* Sidenote: [Google Android Icons](https://www.google.com/design/icons/)

## CLI Samples

```Shell
# just convert a file
svg2png foo.svg

# generate a PNG with a name
svg2png -f foo.svg -n bar.png

# Convert all *.svg files in the current directory to 24dp android png files (generates drawable-* directories)
svg2png --android-small -d .

# Convert 'my_picture.svg' using the android profile 48dp (generates drawable-* directories)
svg2png --android -f my_picture.svg

# Converts 'my-logo.svg' as android logo 48dp, using ic_launcher.png as name, generates into mipmap-* directories
svg2png --android-launch -f my-logo.svg

# convert all files in the directory '/Picures/icons/svg' and use '/Pictures/icons/png' as the output directory
svg2png -d /Picures/icons/svg -o /Pictures/icons/png

# convert with a JSON configuration
svg2png -d . -c my.json

# convert SVG files using the default Android configuration
svg2png -d . -o /dev/workset/android-project/app/src/main/res --android

# convert SVG file based on density ratio: "-r 4" (ratio=4)
java -jar ~/svg2png/jar/svg2png -r 4 -f svg/ic_success.svg -o png/

# convert SVG files based on density ratios for Android and iOS
java -jar svg2png --android-ratio -d svg2png/svg/ -o svg2png/png -c svg2png/src/main/resources/android-ratio.json
java -jar svg2png --ios-ratio -d svg2png/svg/ -o svg2png/png -c svg2png/src/main/resources/ios-ratio.json

# you can always start it like any other java jar file
java -jar svg2png
```

## CLI Usage

        ================================================================================
                                           SVG to PNG                                   
        
        usage: svg2png
            --android         Android default config from mdpi 48x48 -> xxxhdpi
                              192x192.
            --android-small   Android Small default config from mdpi 24x24 ->
                              xxxhdpi 96x96.
            --android-ratio   Android default config from mdpi ratio 1 ->
                              xxxhdpi ratio 4.
            --ios-ratio       iOS default config from 1x ratio ->
                              4x ratio.
         -c <arg>             JSON Config file for the file output.
         -d <arg>             Source directory with one or more files to convert.
         -f <arg>             Source file to convert.
         -h <arg>             Height of the output file.
         -n <arg>             New name to use for all output files.
         -o <arg>             Output directory where to put the file.
         -w <arg>             Width of the output file.
         -r <arg>             Ratio of the output file.

## JSON Android and iOS Config Sample

```JSON
{
    "files": [
        {
            "directory": "drawable-xxxhdpi",
            "nameSuffix": "_24dp",
            "height": 96,
            "width": 96
        },{
            "directory": "drawable-xxhdpi",
            "nameSuffix": "_24dp",
            "height": 72,
            "width": 72
        },{
            "directory": "drawable-xhdpi",
            "nameSuffix": "_24dp",
            "height": 48,
            "width": 48
        },{
            "directory": "drawable-hdpi",
            "nameSuffix": "_24dp",
            "height": 36,
            "width": 36
        },{
            "directory": "drawable-mdpi",
            "nameSuffix": "_24dp",
            "height": 24,
            "width": 24
        }
    ]
}
```
```JSON
{
    "files": [
        {
            "directory": "drawable-xxxhdpi",
            "ratio": 4
        },{
            "directory": "drawable-xxhdpi",
            "ratio": 3
        },{
            "directory": "drawable-xhdpi",
            "ratio": 2
        },{
            "directory": "drawable-hdpi",
            "ratio": 1.5
        },{
            "directory": "drawable-mdpi",
            "ratio": 1
        }
    ]
}
```
```JSON
{
    "files": [
        {
            "directory": "iOS/4x",
            "nameSuffix": "@4x",
            "ratio": 4
        },{
            "directory": "iOS/3x",
            "nameSuffix": "@3x",
            "ratio": 3
        },{
            "directory": "iOS/2x",
            "nameSuffix": "@2x",
            "ratio": 2
        },{
            "directory": "iOS/1.5x",
            "nameSuffix": "@1.5x",
            "ratio": 1.5
        },{
            "directory": "iOS/1x",
            "nameSuffix": "@1x",
            "ratio": 1
        }
    ]
}
```

## Editing

Import to Eclipse:
```
git clone https://github.com/revs87/svg2png
```
Generate .jar in Eclipse from:
```
Main.java -> Export -> Runnable JAR File
```
