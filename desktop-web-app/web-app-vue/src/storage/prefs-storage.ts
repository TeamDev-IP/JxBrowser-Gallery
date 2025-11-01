import { emailTwoFA, TwoFAMethod } from "@/converter/two-fa-method";
import { englishLanguage, LanguageOption } from "@/converter/language";
import { defaultFontSize, FontSizeOption } from "@/converter/font-size";
import { systemTheme, ThemeOption } from "@/converter/theme";

const twoFAKey = "two-factor-authentication";
const biometricAuthenticationKey = "biometric-authentication";
const launchAtStartupKey = "launch-at-startup";
const checkForUpdatesKey = "check-for-updates";
const languageKey = "language";
const fontSizeKey = "font-size";
const themeKey = "vite-ui-theme";
const emailNotificationsKey = "email-notifications";
const desktopNotificationsKey = "desktop-notifications";

class PrefsStorage {
  twoFA(): TwoFAMethod {
    return (localStorage.getItem(twoFAKey) as TwoFAMethod) || emailTwoFA;
  }

  saveTwoFA(tfaMethod: TwoFAMethod) {
    localStorage.setItem(twoFAKey, tfaMethod);
  }

  biometricAuthenticationEnabled(): boolean {
    return localStorage.getItem(biometricAuthenticationKey) === "true";
  }

  saveBiometricAuthentication(isEnabled: boolean) {
    localStorage.setItem(biometricAuthenticationKey, isEnabled ? "true" : "false");
  }

  launchAtStartupEnabled(): boolean {
    return localStorage.getItem(launchAtStartupKey) === "true";
  }

  saveLaunchAtStartup(isEnabled: boolean) {
    localStorage.setItem(launchAtStartupKey, isEnabled ? "true" : "false");
  }

  checkForUpdatesEnabled(): boolean {
    return localStorage.getItem(checkForUpdatesKey) === "true";
  }

  saveCheckForUpdates(isEnabled: boolean) {
    localStorage.setItem(checkForUpdatesKey, isEnabled ? "true" : "false");
  }

  language(): LanguageOption {
    return (localStorage.getItem(languageKey) as LanguageOption) || englishLanguage;
  }

  saveLanguage(language: LanguageOption) {
    localStorage.setItem(languageKey, language);
  }

  theme(): ThemeOption {
    return (localStorage.getItem(themeKey) as ThemeOption) || systemTheme;
  }

  saveTheme(theme: ThemeOption) {
    localStorage.setItem(themeKey, theme);
  }

  fontSize(): FontSizeOption {
    return (localStorage.getItem(fontSizeKey) as FontSizeOption) || defaultFontSize;
  }

  saveFontSize(option: FontSizeOption) {
    localStorage.setItem(fontSizeKey, option);
  }

  emailNotificationsEnabled(): boolean {
    return localStorage.getItem(emailNotificationsKey) === "true";
  }

  saveEmailNotifications(isEnabled: boolean) {
    localStorage.setItem(emailNotificationsKey, isEnabled ? "true" : "false");
  }

  desktopNotificationsEnabled(): boolean {
    return localStorage.getItem(desktopNotificationsKey) === "true";
  }

  saveDesktopNotifications(isEnabled: boolean) {
    localStorage.setItem(desktopNotificationsKey, isEnabled ? "true" : "false");
  }
}

const prefsStorage = new PrefsStorage();

export { prefsStorage };
