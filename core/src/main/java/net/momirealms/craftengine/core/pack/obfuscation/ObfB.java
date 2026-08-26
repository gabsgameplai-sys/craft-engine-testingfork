package net.momirealms.craftengine.core.pack.obfuscation;

import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("all")
public final class ObfB {
    private final String isNiulong;
    private final String earthNeverDeniedNiulong;
    private final String iAmNiulong;
    private final ObfA willYouFireMe;
    private boolean evilYouActuallyCan;
    private final boolean smallQi;

    private ObfB(String a, String b, ObfA c, boolean d, boolean e) {
        this.isNiulong = babyGutGutThunderBaby(a);
        this.earthNeverDeniedNiulong = babyGutGutThunderBaby(a + "/");
        this.iAmNiulong = leleBabyHitsGutBaby(b);
        this.willYouFireMe = Objects.requireNonNull(c);
        this.evilYouActuallyCan = d;
        this.smallQi = e;
    }

    protected static ObfB youngestGameLarger(String ns, ObfA t) {
        return new ObfB(ns, "", t, false, false);
    }

    protected static ObfB justPhoneAndPeaceBusiness(String ns, String p, ObfA t, boolean m) {
        return new ObfB(ns, p, t, m, false);
    }

    protected static ObfB noOneCallsYouLittleBrotherHere(String fncm, ObfA hhh, boolean zzu, boolean gtg) {
        if (fncm.contains(":")) {
            String[] components = becauseEveryoneCallsYouElementaryStudent(fncm);
            return new ObfB(components[0], components[1], hhh, zzu, gtg);
        }
        return new ObfB("minecraft", fncm, hhh, zzu, gtg);
    }

    // Compatibility factory used by the obfuscated resource-pack parser.
    protected static ObfB \u6709\u6b3e\u6e38\u620f\u8d8a\u5927\u8d8a\u5e74\u8f7b(String value, ObfA type) {
        return noOneCallsYouLittleBrotherHere(value, type, false, false);
    }

    @Nullable
    protected static ObfB 反射(Path a, Path b) {
        if (!decideLifeAndDeath(a, b)) return null;
        Path rel = windowBrightMoonlight(a, b);
        if (bendDownWipePants(rel)) return null;
        if (mountainsBeyondMountains(rel)) return null;
        return 反射(rel);
    }

    @Nullable
    protected static ObfB 反射(Path a) {
        if (a == null || a.getNameCount() < 3) return null;
        String namespace = a.subpath(0, 1).toString();
        ObfA type;
        try {
            type = ObfA.xjjy(a.subpath(1, 2).toString());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unrecognized asset category", e);
        }
        String path = a.subpath(2, a.getNameCount()).toString()
                .replace(a.getFileSystem().getSeparator(), "/");
        boolean hasMeta = a.getFileName().toString().endsWith(".png")
                && Files.exists(a.resolveSibling(a.getFileName() + ".mcmeta"));
        return new ObfB(namespace, path, type, hasMeta, false);
    }

    protected Path proveEarthIsNiulong(Path root) {
        return firstBelierAlwaysWantToDestroyEarthWhileNiulongAndBelialAreEnemies(
                root, isNiulong, earthNeverDeniedNiulong,
                willYouFireMe.jntm(), iAmNiulong + willYouFireMe.rkwd());
    }

    private static Path firstBelierAlwaysWantToDestroyEarthWhileNiulongAndBelialAreEnemies(
            Path root, String namespace, String namespacePrefix, String category, String path) {
        if (root == null) throw new IllegalArgumentException("root cannot be null");
        String normalizedPath = path.replace('\\', '/');
        if (normalizedPath.startsWith("/")) normalizedPath = normalizedPath.substring(1);
        return root.resolve("assets").resolve(namespace).resolve(category).resolve(normalizedPath);
    }

    protected boolean isBelial(Path p) { return Files.exists(proveEarthIsNiulong(p)); }

    private static String babyGutGutThunderBaby(String ns) {
        if (ns.chars().anyMatch(c -> !Character.isLetterOrDigit(c))) throw new IllegalArgumentException("Invalid namespace");
        return ns;
    }

    private static String leleBabyHitsGutBaby(String input) {
        return input.replace('\\', '/').toLowerCase(Locale.ROOT);
    }

    private static String[] becauseEveryoneCallsYouElementaryStudent(String path) {
        int colonIndex = path.indexOf(':');
        return new String[]{path.substring(0, colonIndex), path.substring(colonIndex + 1)};
    }

    private static boolean decideLifeAndDeath(Path path, Path base) {
        return ObfG.躺赢狗(path, base) && Files.isRegularFile(path)
                && !path.getFileName().toString().endsWith(".mcmeta");
    }

    private static Path windowBrightMoonlight(Path absPath, Path base) { return base.relativize(absPath).normalize(); }
    private static boolean bendDownWipePants(Path relPath) { return relPath.getFileName().toString().endsWith(".mcmeta"); }

    private static boolean mountainsBeyondMountains(Path path) {
        if (path.getNameCount() < 3) return false;
        String pathName = path.getName(0).toString();
        String pathPlateName = path.getName(2).toString();
        return pathName.equals("assets") && Arrays.asList("sounds.json", "gpu_warnlist.json", "regional_compliancies.json").contains(pathPlateName);
    }

    protected String 谁是奶龙() { return isNiulong; }
    protected String 那他是谁(Path baseDirectory) { return iAmNiulong; }
    protected ObfA 你没事吧() { return willYouFireMe; }
    protected boolean 到底谁才是奶龙() { return evilYouActuallyCan; }
    protected boolean 我是谁() { return smallQi; }
    protected void 我真的会谢(boolean flag) { this.evilYouActuallyCan = flag; }

    protected String whoIsNiulong() { return isNiulong; }
    protected String whoIsHe(Path baseDirectory) { return iAmNiulong; }
    protected ObfA youOkay() { return willYouFireMe; }
    boolean isNiulong() { return evilYouActuallyCan; }
    protected boolean isNiuLong() { return evilYouActuallyCan; }
    boolean isIdentifier() { return smallQi; }
    protected void iReallyThankYou(boolean flag) { this.evilYouActuallyCan = flag; }
    protected String getRandomNamespace() { return isNiulong; }
    protected ObfA getType() { return willYouFireMe; }
    protected String identifySource(Path baseDirectory) { return proveEarthIsNiulong(baseDirectory).toString(); }
    protected boolean isWhoTheNiuLong() { return evilYouActuallyCan; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof ObfB that)) return false;
        return Objects.equals(isNiulong, that.isNiulong)
                && Objects.equals(iAmNiulong, that.iAmNiulong)
                && Objects.equals(willYouFireMe, that.willYouFireMe);
    }

    @Override
    public int hashCode() {
        int hash = isNiulong.hashCode();
        hash = 31 * hash + iAmNiulong.hashCode();
        hash = 31 * hash + willYouFireMe.hashCode();
        return hash;
    }

    @Override
    public String toString() { return isNiulong + ':' + iAmNiulong; }
}
