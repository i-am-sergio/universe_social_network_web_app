import time
import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from HtmlTestRunner import HTMLTestRunner
import os
import re

EXPECTED = "Expected: "
RESULT = "|Result: "
USERNAME = "user1@gmail.com"
TEST_POST_SUCCESS = "Test post success 1"
IMAGE_NAME = "misato.jpg"

class TestPost(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get("http://localhost:3000/")
        self.driver.delete_all_cookies()
        self.driver.execute_script("window.localStorage.clear()")
        current_directory = os.path.dirname(__file__)
        self.image_path = os.path.join(current_directory, IMAGE_NAME)
        self.xpath_username = """//*[@id="root"]/div/div[3]/div[2]/form/div[1]/input"""
        self.xpath_password = """//*[@id="root"]/div/div[3]/div[2]/form/div[2]/input"""
        self.xpath_btn_login = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/button"""
        self.xpath_text_post = """//*[@id="root"]/div/div[3]/div[2]/div[1]/div/input"""
        self.xpath_image_post = """//*[@id="root"]/div/div[3]/div[2]/div[1]/div/div/button[1]"""
        self.xpath_button_post = """//*[@id="root"]/div/div[3]/div[2]/div[1]/div/div/button[5]"""
        self.xpath_text_post_uploaded = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div[1]/div[2]/span[2]"""
        self.xpath_image_post_uploaded = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div[1]/img"""
        self.xpath_input_file = """//input[@type='file']"""
        self.section_posts = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div[1]"""
    

    def tearDown(self):
        self.driver.close()
    
    def test_post_with_image_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 1200)
        wait_element.until(EC.visibility_of_element_located((By.XPATH, self.section_posts)))
        self.driver.find_element(By.XPATH, self.xpath_text_post).send_keys(TEST_POST_SUCCESS)
        input_imagen = self.driver.find_element(By.XPATH, self.xpath_input_file)
        input_imagen.send_keys(self.image_path)
        self.driver.find_element(By.XPATH, self.xpath_button_post).click()
        
        time.sleep(12)
        result_text = self.driver.find_element(By.XPATH, self.xpath_text_post_uploaded).text
        result_image = self.driver.find_element(By.XPATH, self.xpath_image_post_uploaded).get_attribute("src")

        cloudinary_pattern = r"http://res.cloudinary.com/.+/image/upload/v\d+/.+\.\w+"
        is_cloudinary_url = re.match(cloudinary_pattern, result_image) is not None
        print("La ruta de la imagen es:", self.image_path)
        print("La ruta de la imagen subida es:", result_image)
        print(EXPECTED, TEST_POST_SUCCESS)
        print(RESULT, result_text)
        self.assertEqual(result_text, TEST_POST_SUCCESS)
        self.assertTrue(is_cloudinary_url, f"{result_image} no es una URL válida de Cloudinary")

    
    def test_post_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 20)
        wait_element.until(EC.visibility_of_element_located((By.XPATH, self.section_posts)))
        self.driver.find_element(By.XPATH, self.xpath_text_post).send_keys(TEST_POST_SUCCESS)
        self.driver.find_element(By.XPATH, self.xpath_button_post).click()
        
        time.sleep(12)
        result_text = self.driver.find_element(By.XPATH, self.xpath_text_post_uploaded).text
        print(EXPECTED, TEST_POST_SUCCESS)
        print(RESULT, result_text)
        self.assertEqual(result_text, TEST_POST_SUCCESS)
        
    def test_post_without_text(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 20)
        wait_element.until(EC.visibility_of_element_located((By.XPATH, self.section_posts)))
        self.driver.find_element(By.XPATH, self.xpath_button_post).click()
        
        time.sleep(12)
        result_text = self.driver.find_element(By.XPATH, self.xpath_text_post_uploaded).text
        print(EXPECTED, "")
        print(RESULT, result_text)
        self.assertEqual(result_text, "")
        