import time
import datetime

from selenium import webdriver
import pymysql

class Movie:
    def __init__(self, date, start_time, running_time, movie_title, company_type, region, hall):
        self.date = date
        self.start_time = start_time
        self.running_time = running_time
        self.movie_title = movie_title
        self.company_type = company_type
        self.region = region
        self.hall = hall

def connect():
    conn = pymysql.connect(host='15.165.112.160', user='mvcteam', password='springmaster1', db='mvcteam', charset='utf8')
    return conn

def insert(movie):
    conn = connect()
    try:
        with conn.cursor() as curs:
            sql = 'insert into movie(date,start_time,running_time,movie_title,company_type,region,hall)values (%s, %s, %s, %s, %s, %s, %s)'
            curs.execute(sql, (movie.date, movie.start_time, movie.running_time, movie.movie_title, movie.company_type, movie.region, movie.hall))
        conn.commit()
    finally:
        conn.close()

driver = webdriver.Firefox(executable_path='C:/Users/hello world/Desktop/geckodriver.exe')

# CGV-----------------------------------------------------------------------------------------------------------------------
# 오늘부터 3일치 크롤링
def get3dayCGV(movieList):
    driver.get('http://section.cgv.co.kr/theater/popup/r_TimeTable.aspx?code=0196')
    # 당일

    date = driver.find_element_by_xpath('/html/body/form/div[3]/div/div/div/div/div[2]/div/div/div/ul/li[1]/a/span').text
    date = '2020-' + date[:2] + "-" + date[3:]
    movieList = getCGV(date, movieList)

    # 1일후
    driver.find_element_by_xpath('/html/body/form/div[3]/div/div/div/div/div[2]/div/div/div/ul/li[2]/a').click()
    date = driver.find_element_by_xpath('/html/body/form/div[3]/div/div/div/div/div[2]/div/div/div/ul/li[2]/a/span').text
    date = '2020-' + date[:2] + "-" + date[3:]
    movieList = getCGV(date, movieList)


    # # 2일후 - 새벽3시 기준 CGV는 2일치 데이터만 제공하고있음
    # driver.find_element_by_xpath('/html/body/form/div[3]/div/div/div/div/div[2]/div/div/div/ul/li[3]/a').click()
    # date = driver.find_element_by_xpath('/html/body/form/div[3]/div/div/div/div/div[2]/div/div/div/ul/li[3]/a/span').text
    # date = '2020-' + date[:2] + "-" + date[3:]
    # movieList = getCGV(date, movieList)
    return movieList

# 서현 CGV영화리스트 크롤링 상영시간-60 / 상영관-1 로 고정
def getCGV(date, movieList):
    runningTime = 60
    company_type = "CGV"
    region = "서현"

    time.sleep(1)
    trList = driver.find_element_by_tag_name('tbody').find_elements_by_tag_name('tr')

    for tr in trList:
        movieTItle = tr.find_element_by_class_name('txt').get_property('title')
        hall = tr.find_elements_by_tag_name('td')[1].text
        startTimeList = tr.find_element_by_class_name('time').find_elements_by_tag_name('a')
        for startTime in startTimeList:
            movie = Movie(date, startTime.text, runningTime, movieTItle, company_type, region, "1")
            movieList.append(movie)
    return movieList

# megaBox-----------------------------------------------------------------------------------------------------------------------
# 오늘부터 3일치 크롤링
def get3dayMegabox(movieList):
    driver.get('https://www.megabox.co.kr/theater/time?brchNo=4631')

    # 당일
    date = driver.find_element_by_xpath('/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[2]/div/button[3]').get_attribute('date-data')
    date = date[:4] + "-" + date[5:7] + "-" + date[8:]
    movieList = getMegabox(date, movieList)

    # 1일후
    driver.find_element_by_xpath('/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[2]/div/button[4]').click()
    date = driver.find_element_by_xpath('/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[2]/div/button[4]').get_attribute('date-data')
    date = date[:4] + "-" + date[5:7] + "-" + date[8:]
    movieList = getMegabox(date, movieList)


    # 2일후
    driver.find_element_by_xpath('/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[2]/div/button[5]').click()
    date = driver.find_element_by_xpath('/html/body/div[2]/div[3]/div[2]/div[2]/div[2]/div[2]/div[2]/div/div[1]/div[2]/div/button[5]').get_attribute('date-data')
    date = date[:4] + "-" + date[5:7] + "-" + date[8:]
    movieList = getMegabox(date, movieList)
    return movieList

# 서현 메가박스영화리스트 크롤링 상영관-1 로 고정
def getMegabox(date, movieList):
    company_type = "MEGABOX"
    region = "서현"

    time.sleep(1)
    theaterBoxList = driver.find_elements_by_class_name('theater-list')

    for theaterBox in theaterBoxList:
        movie_title = theaterBox.find_element_by_tag_name('a').text
        running_time = theaterBox.find_element_by_class_name('infomation').text
        running_time = running_time.split()[1][:-1]
        hallboxList = theaterBox.find_elements_by_class_name('theater-type-box')
        for hallBox in hallboxList:
            hall = hallBox.find_element_by_class_name('theater-name').text
            startTimeList = hallBox.find_elements_by_class_name('time')
            for startTimeTag in startTimeList:
                start_time = startTimeTag.text
                movie = Movie(date, start_time, running_time, movie_title, company_type, region, "1")
                movieList.append(movie)
    return movieList

# lotte-----------------------------------------------------------------------------------------------------------------------
# 오늘부터 3일치 크롤링
def get3dayLotte(movieList):
    driver.get('https://www.lottecinema.co.kr/NLCHS/Cinema/Detail?divisionCode=1&detailDivisionCode=2&cinemaID=3041')
    time.sleep(2)

    month = driver.find_element_by_class_name('month').text[:-1]
    # 당일
    day = driver.find_element_by_xpath('/html/body/div[7]/ul/li[1]/div/div[1]/div/ul/div[1]/div/div[2]/li/span/label/strong').text
    if len(day) < 2:
        day = '0' + day
    date = "2020-" + month + "-" + day
    movieList = geyLotte(date, movieList)

    # 1일후
    driver.find_element_by_xpath('/html/body/div[7]/ul/li[1]/div/div[1]/div/ul/div[1]/div/div[3]/li/span/label').click()
    day = driver.find_element_by_xpath('/html/body/div[7]/ul/li[1]/div/div[1]/div/ul/div[1]/div/div[3]/li/span/label/strong').text
    if len(day) < 2:
        day = '0' + day
    date = "2020-" + month + "-" + day
    movieList = geyLotte(date, movieList)

    # 2일후
    driver.find_element_by_xpath('/html/body/div[7]/ul/li[1]/div/div[1]/div/ul/div[1]/div/div[4]/li/span/label').click()
    day = driver.find_element_by_xpath('/html/body/div[7]/ul/li[1]/div/div[1]/div/ul/div[1]/div/div[4]/li/span/label/strong').text
    if len(day) < 2:
        day = '0' + day
    date = "2020-" + month + "-" + day
    movieList = geyLotte(date, movieList)
    return movieList

# 서현 메가박스영화리스트 크롤링 상영관-1 로 고정
def geyLotte(date, movieList):
    company_type = "롯데시네마"
    region = "성남중앙(신흥역)"

    time.sleep(1)
    movieBoxList = driver.find_elements_by_class_name('time_select_wrap')

    for movieBox in movieBoxList:
        movie_title = movieBox.find_element_by_class_name('list_tit').find_element_by_tag_name('p').text
        timeboxRowList = movieBox.find_elements_by_class_name('list_time')
        for timeBoxRow in timeboxRowList:
            timeBoxList = timeBoxRow.find_elements_by_tag_name('li')
            for timebox in timeBoxList:
                hall = timebox.find_element_by_class_name('hall').text
                movieTime = timebox.find_element_by_class_name('time')
                start_time = movieTime.find_element_by_tag_name('strong').text
                end_time = movieTime.find_element_by_class_name('tooltip').get_attribute('innerHTML').split()[1]
                end_time = end_time[:2] + end_time[3:]
                movie = Movie(date, start_time, end_time, movie_title, company_type, region, "1")
                movieList.append(movie)
    return movieList

if __name__ == '__main__':
    # CGV
    # 크롤링 실행
    movieList = []
    movieList = get3dayCGV(movieList)
    # 크롤링결과 프린트 및 db입력
    for movie in movieList:
        print(movie.date, movie.start_time, movie.running_time, movie.movie_title, movie.company_type, movie.region, movie.hall)
        insert(movie)
    print('-------------------------------------------------------')

    # megabox
    # 크롤링 실행
    movieList = []
    movieList = get3dayMegabox(movieList)
    # 크롤링결과 프린트 및 db입력
    for movie in movieList:
        print(movie.date, movie.start_time, movie.running_time, movie.movie_title, movie.company_type, movie.region, movie.hall)
        insert(movie)
    print('-------------------------------------------------------')

    # LOTTE
    # 크롤링 실행
    movieList = []
    movieList = get3dayLotte(movieList)
    # 크롤링결과 프린트 및 db입력
    for movie in movieList:
        print(movie.date, movie.start_time, movie.running_time, movie.movie_title, movie.company_type, movie.region, movie.hall)
        insert(movie)