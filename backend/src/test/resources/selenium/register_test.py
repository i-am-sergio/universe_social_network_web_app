import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from HtmlTestRunner import HTMLTestRunner
import time

FIRST_NAME = "Shinji3000"
LAST_NAME = "Ikari3000"
USERNAME = "user3000@gmail.com"
PASSWORD = "admin"

class TestRegister(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.driver = webdriver.Chrome()

    @classmethod
    def tearDownClass(cls):
        cls.driver.quit()


    def setUp(self):
        self.driver.get("http://localhost:3000/")
        self.xpath_btn_register = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/button[2]"""
        self.xpath_btn_sign_up = """//*[@id="root"]/div/div[3]/div[2]/form/div[4]/button[1]"""
        self.xpath_first_name = """//*[@id="root"]/div/div[3]/div[2]/form/div[1]/input[1]"""
        self.xpath_last_name = """//*[@id="root"]/div/div[3]/div[2]/form/div[1]/input[2]"""
        self.xpath_username = """//*[@id="root"]/div/div[3]/div[2]/form/div[2]/input"""
        self.xpath_password = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/input[1]"""
        self.xpath_password_confirm = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/input[2]"""
        self.xpath_home_name = """//*[@id="root"]/div/div[3]/div[1]/div[2]/div[2]/span[1]"""
        self.xpath_miperfil = """//*[@id="root"]/div/div[3]/div[1]/div[2]/span/a"""
        self.xpath_logout = """//*[@id="root"]/div/div[3]/div[1]/div[2]/button"""
        self.xpath_register_page = """//*[@id="root"]/div/div[3]/div[2]/form/h3"""
        self.driver.find_element(By.XPATH, self.xpath_btn_register).click()


    def tearDown(self):
        time.sleep(1)

    def test_register_success(self):
        self.driver.find_element(By.XPATH, self.xpath_first_name).send_keys(FIRST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_last_name).send_keys(LAST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_password_confirm).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(10)
        result = self.driver.find_element(By.XPATH, self.xpath_home_name).text
        self.quit_session()
        self.assertEqual(result, FIRST_NAME + " " + LAST_NAME)
                

    def test_register_missing_first_name(self):
        self.driver.find_element(By.XPATH, self.xpath_last_name).send_keys(LAST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_password_confirm).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(1)
        result = self.driver.find_element(By.XPATH, self.xpath_register_page).text
        self.assertEqual(result, "Register")

    def test_register_missing_last_name(self):
        self.driver.find_element(By.XPATH, self.xpath_first_name).send_keys(FIRST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_password_confirm).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(1)
        result = self.driver.find_element(By.XPATH, self.xpath_register_page).text
        self.assertEqual(result, "Register")

    def test_register_missing_username(self):
        self.driver.find_element(By.XPATH, self.xpath_first_name).send_keys(FIRST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_last_name).send_keys(LAST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_password_confirm).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(1)
        result = self.driver.find_element(By.XPATH, self.xpath_register_page).text
        self.assertEqual(result, "Register")

    def test_register_missing_password(self):
        self.driver.find_element(By.XPATH, self.xpath_first_name).send_keys(FIRST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_last_name).send_keys(LAST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password_confirm).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(1)
        result = self.driver.find_element(By.XPATH, self.xpath_register_page).text
        self.assertEqual(result, "Register")

    def test_register_missing_password_confirm(self):
        self.driver.find_element(By.XPATH, self.xpath_first_name).send_keys(FIRST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_last_name).send_keys(LAST_NAME)
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys(PASSWORD)
        self.driver.find_element(By.XPATH, self.xpath_btn_sign_up).click()
        time.sleep(1)
        result = self.driver.find_element(By.XPATH, self.xpath_register_page).text
        self.assertEqual(result, "Register")

    def quit_session(self):
        self.driver.find_element(By.XPATH, self.xpath_miperfil).click()
        time.sleep(5)
        self.driver.find_element(By.XPATH, self.xpath_logout).click()
